import { useCallback, useEffect, useMemo, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { getCurrentUser, logoutUser } from "../services/authService";
import {
  createCategory,
  createTransaction,
  deleteTransaction,
  getCategories,
  getTransactions,
  updateTransaction,
} from "../services/transactionService";
import "./Transactions.css";

const currentMonth = () => {
  const now = new Date();
  return `${now.getFullYear()}-${String(now.getMonth() + 1).padStart(2, "0")}`;
};
const today = () => new Date().toLocaleDateString("en-CA");
const monthRange = (month) => ({ start: `${month}-01`, end: `${month}-${new Date(Number(month.slice(0, 4)), Number(month.slice(5, 7)), 0).getDate()}` });
const money = (value) => new Intl.NumberFormat("en-IN", { style: "currency", currency: "INR" }).format(Number(value || 0));
const blankForm = () => ({ type: "EXPENSE", amount: "", categoryId: "", transactionDate: today(), notes: "" });

export default function Transactions() {
  const navigate = useNavigate();
  const [month, setMonth] = useState(currentMonth);
  const [transactions, setTransactions] = useState([]);
  const [categories, setCategories] = useState([]);
  const [user, setUser] = useState(null);
  const [form, setForm] = useState(blankForm);
  const [editingId, setEditingId] = useState(null);
  const [categoryName, setCategoryName] = useState("");
  const [categoryType, setCategoryType] = useState("EXPENSE");
  const [showCategoryForm, setShowCategoryForm] = useState(false);
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState("");
  const [notice, setNotice] = useState("");

  const availableCategories = useMemo(() => categories.filter((category) => category.type === form.type), [categories, form.type]);
  const totals = useMemo(() => transactions.reduce((sum, tx) => {
    if (tx.type === "INCOME") sum.income += Number(tx.amount);
    else sum.expenses += Number(tx.amount);
    return sum;
  }, { income: 0, expenses: 0 }), [transactions]);

  const load = useCallback(async () => {
    setLoading(true); setError("");
    try {
      const range = monthRange(month);
      const [txs, cats, currentUser] = await Promise.all([
        getTransactions(range.start, range.end), getCategories(), getCurrentUser(),
      ]);
      setTransactions(txs || []); setCategories(cats || []); setUser(currentUser);
    } catch (err) {
      setError(err.message || "Could not load transactions.");
      if (/unauthorized|sign in|401/i.test(err.message || "")) navigate("/login", { replace: true });
    } finally { setLoading(false); }
  }, [month, navigate]);

  useEffect(() => { load(); }, [load]);
  useEffect(() => {
    if (!editingId) {
      const first = categories.find((category) => category.type === form.type);
      setForm((old) => ({ ...old, categoryId: first ? String(first.id) : "" }));
    }
  }, [form.type, categories, editingId]);

  function changeType(type) {
    const first = categories.find((category) => category.type === type);
    setForm((old) => ({ ...old, type, categoryId: first ? String(first.id) : "" }));
  }

  function startEdit(tx) {
    setEditingId(tx.id);
    setForm({ type: tx.type, amount: String(tx.amount), categoryId: String(tx.categoryId), transactionDate: tx.transactionDate, notes: tx.notes || "" });
    setError(""); setNotice("");
    window.scrollTo({ top: 0, behavior: "smooth" });
  }

  function cancelEdit() { setEditingId(null); setForm(blankForm()); setError(""); }

  async function submitTransaction(event) {
    event.preventDefault(); setSaving(true); setError(""); setNotice("");
    if (!form.categoryId) { setError(`Please create or select a ${form.type.toLowerCase()} category first.`); setSaving(false); return; }
    try {
      const payload = {
        type: form.type,
        amount: Number(form.amount),
        categoryId: Number(form.categoryId),
        transactionDate: form.transactionDate,
        notes: form.notes.trim(),
      };
      if (editingId) await updateTransaction(editingId, payload);
      else await createTransaction(payload);
      setNotice(editingId ? "Transaction updated." : "Transaction added.");
      setEditingId(null); setForm(blankForm());
      await load();
    } catch (err) { setError(err.message || "Unable to save transaction."); }
    finally { setSaving(false); }
  }

  async function removeTransaction(tx) {
    if (!window.confirm(`Delete this ${tx.type.toLowerCase()} of ${money(tx.amount)}? This cannot be undone.`)) return;
    setError(""); setNotice("");
    try {
      await deleteTransaction(tx.id);
      if (editingId === tx.id) cancelEdit();
      setNotice("Transaction deleted."); await load();
    } catch (err) { setError(err.message || "Unable to delete transaction."); }
  }

  async function submitCategory(event) {
    event.preventDefault(); setError(""); setNotice("");
    try {
      const created = await createCategory({ name: categoryName.trim(), type: categoryType });
      const next = [...categories, created].sort((a, b) => a.name.localeCompare(b.name));
      setCategories(next); setCategoryName(""); setCategoryType(form.type); setShowCategoryForm(false);
      if (created.type === form.type) setForm((old) => ({ ...old, categoryId: String(created.id) }));
      setNotice(`Category “${created.name}” created.`);
    } catch (err) { setError(err.message || "Unable to create category."); }
  }

  async function handleLogout() {
    try { await logoutUser(); } finally { navigate("/login", { replace: true }); }
  }

  return (
    <div className="tx-layout">
      <aside className="tx-sidebar">
        <Link to="/dashboard" className="tx-brand"><span>₹</span> FinanceFlow</Link>
        <nav className="tx-nav">
          <Link to="/dashboard">▦ <span>Dashboard</span></Link>
          <Link className="selected" to="/transactions">⇄ <span>Transactions</span></Link>
        </nav>
        <div className="tx-profile"><strong>{user?.name || "Your account"}</strong><small>{user?.email || ""}</small><button className="tx-secondary full" onClick={handleLogout}>Sign out</button></div>
      </aside>

      <main className="tx-main">
        <header className="tx-header">
          <div><p className="tx-eyebrow">PERSONAL FINANCE</p><h1>Transactions</h1><p className="tx-muted">Create, review, edit and delete your income and expenses.</p></div>
          <label className="tx-month">Report month<input type="month" value={month} max={currentMonth()} onChange={(e) => { setMonth(e.target.value); setEditingId(null); }} /></label>
        </header>

        {error && <div className="tx-alert error" role="alert">{error}<button className="tx-alert-close" onClick={() => setError("")}>Dismiss</button></div>}
        {notice && <div className="tx-alert success" role="status">{notice}<button className="tx-alert-close" onClick={() => setNotice("")}>Dismiss</button></div>}

        <section className="tx-stats">
          <article><span>Income this month</span><strong className="tx-positive">{money(totals.income)}</strong></article>
          <article><span>Expenses this month</span><strong className="tx-negative">{money(totals.expenses)}</strong></article>
          <article><span>Remaining balance</span><strong>{money(totals.income - totals.expenses)}</strong></article>
        </section>

        <section className="tx-card">
          <div className="tx-section-heading"><div><h2>{editingId ? "Edit transaction" : "Add a transaction"}</h2><p>Amounts are stored in your account and update the dashboard.</p></div>{editingId && <button className="tx-secondary" onClick={cancelEdit}>Cancel edit</button>}</div>
          <form className="tx-form" onSubmit={submitTransaction}>
            <label>Transaction type<select value={form.type} onChange={(e) => changeType(e.target.value)}><option value="EXPENSE">Expense</option><option value="INCOME">Income</option></select></label>
            <label>Amount (₹)<input type="number" min="0.01" max="9999999999999.99" step="0.01" value={form.amount} onChange={(e) => setForm({ ...form, amount: e.target.value })} placeholder="e.g. 1250.00" required /></label>
            <label>Category<select value={form.categoryId} onChange={(e) => setForm({ ...form, categoryId: e.target.value })} required><option value="">Select category</option>{availableCategories.map((c) => <option key={c.id} value={c.id}>{c.name}</option>)}</select></label>
            <label>Date<input type="date" value={form.transactionDate} max={today()} onChange={(e) => setForm({ ...form, transactionDate: e.target.value })} required /></label>
            <label className="tx-notes">Notes (optional)<input maxLength="500" value={form.notes} onChange={(e) => setForm({ ...form, notes: e.target.value })} placeholder="e.g. Monthly salary, groceries" /></label>
            <div className="tx-form-actions"><button disabled={saving} type="submit">{saving ? "Saving…" : editingId ? "Save changes" : "Add transaction"}</button><button type="button" className="tx-secondary" onClick={() => setShowCategoryForm((v) => !v)}>{showCategoryForm ? "Hide category form" : "+ New category"}</button></div>
          </form>
          {availableCategories.length === 0 && <p className="tx-inline-help">No {form.type.toLowerCase()} categories yet. Create one below to continue.</p>}
          {showCategoryForm && <form className="tx-category-form" onSubmit={submitCategory}><h3>Create category</h3><label>Category name<input value={categoryName} maxLength={100} onChange={(e) => setCategoryName(e.target.value)} placeholder="e.g. Groceries or Salary" required /></label><label>Applies to<select value={categoryType} onChange={(e) => setCategoryType(e.target.value)}><option value="EXPENSE">Expense</option><option value="INCOME">Income</option></select></label><button type="submit">Create category</button></form>}
        </section>

        <section className="tx-card">
          <div className="tx-section-heading"><div><h2>Transactions for {month}</h2><p>{transactions.length} record{transactions.length === 1 ? "" : "s"} · newest first</p></div><button className="tx-secondary" onClick={load} disabled={loading}>{loading ? "Refreshing…" : "Refresh"}</button></div>
          {loading ? <p className="tx-empty">Loading transactions…</p> : transactions.length === 0 ? <div className="tx-empty"><strong>No transactions for this month</strong><span>Add your first income or expense using the form above.</span></div> : (
            <div className="tx-table-wrap"><table className="tx-table"><thead><tr><th>Date</th><th>Category</th><th>Type</th><th>Notes</th><th className="tx-amount">Amount</th><th>Actions</th></tr></thead><tbody>{transactions.map((tx) => <tr key={tx.id}><td>{tx.transactionDate}</td><td><strong>{tx.category}</strong></td><td><span className={`tx-pill ${tx.type === "INCOME" ? "income" : "expense"}`}>{tx.type}</span></td><td className="tx-notes-cell">{tx.notes || "—"}</td><td className={`tx-amount ${tx.type === "INCOME" ? "tx-positive" : "tx-negative"}`}>{tx.type === "INCOME" ? "+" : "−"}{money(tx.amount)}</td><td><div className="tx-row-actions"><button className="tx-secondary small" onClick={() => startEdit(tx)}>Edit</button><button className="tx-danger small" onClick={() => removeTransaction(tx)}>Delete</button></div></td></tr>)}</tbody></table></div>
          )}
        </section>
        <p className="tx-footnote">Transactions are private to your signed-in account. Deleting a record permanently changes your totals and dashboard summaries.</p>
      </main>
    </div>
  );
}
