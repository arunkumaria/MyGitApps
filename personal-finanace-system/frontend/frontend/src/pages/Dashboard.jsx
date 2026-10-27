import { useCallback, useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { getDashboard } from "../services/dashboardService";
import { getCurrentUser, logoutUser } from "../services/authService";
import "./Dashboard.css";

const currentMonth = () => {
	const now = new Date();
	return `${now.getFullYear()}-${String(now.getMonth() + 1).padStart(2, "0")}`;
};

const money = (amount) =>
	new Intl.NumberFormat("en-IN", {
		style: "currency",
		currency: "INR",
		maximumFractionDigits: 2,
	}).format(Number(amount ?? 0));

const displayDate = (date) =>
	new Date(`${date}T00:00:00`).toLocaleDateString("en-IN", {
		day: "2-digit",
		month: "short",
		year: "numeric",
	});

export default function Dashboard() {
	const navigate = useNavigate();

	const [month, setMonth] = useState(currentMonth);
	const [dashboard, setDashboard] = useState(null);
	const [user, setUser] = useState(null);
	const [loading, setLoading] = useState(true);
	const [error, setError] = useState("");

	const loadDashboard = useCallback(async () => {
		setLoading(true);
		setError("");

		try {
			const [summary, currentUser] = await Promise.all([
				getDashboard(month),
				getCurrentUser(),
			]);

			setDashboard(summary);
			setUser(currentUser);
		} catch (err) {
			setError(err.message);
			setDashboard(null);

			if (/sign in|unauthorized|401/i.test(err.message)) {
				navigate("/login", { replace: true });
			}
		} finally {
			setLoading(false);
		}
	}, [month, navigate]);

	useEffect(() => {
		loadDashboard();
	}, [loadDashboard]);

	async function handleLogout() {
		try {
			await logoutUser();
		} finally {
			navigate("/login", { replace: true });
		}
	}

	const income = Number(dashboard?.totalIncome ?? 0);
	const expenses = Number(dashboard?.totalExpenses ?? 0);
	const balance = Number(dashboard?.balance ?? income - expenses);
	const maxAmount = Math.max(income, expenses, 1);

	return (
		<div className="dashboard-layout">
			<aside className="dashboard-sidebar">
				<Link to="/dashboard" className="brand">
					<span className="brand-icon">₹</span>
					<span>FinanceFlow</span>
				</Link>

				<nav className="dashboard-nav">
					<Link className="nav-link active" to="/dashboard">
						<span>▦</span> Dashboard
					</Link>
					<Link className="nav-link" to="/transactions">
						<span>⇄</span> Transactions
					</Link>
					<Link className="nav-link" to="/budgets">
						<span>◎</span> Budgets
					</Link>
				</nav>

				<div className="sidebar-bottom">
					<div className="profile-summary">
						<div className="avatar">
							{user?.name?.charAt(0)?.toUpperCase() || "U"}
						</div>
						<div className="profile-text">
							<strong>{user?.name || "User"}</strong>
							<span>{user?.email || ""}</span>
						</div>
					</div>
					<button className="logout-button" onClick={handleLogout}>
						Sign out
					</button>
				</div>
			</aside>

			<main className="dashboard-main">
				<header className="dashboard-header">
					<div>
						<p className="eyebrow">PERSONAL FINANCE</p>
						<h1>Dashboard</h1>
						<p className="muted">
							Welcome back{user?.name ? `, ${user.name}` : ""}.
							Here's your financial overview.
						</p>
					</div>

					<label className="month-picker">
						<span>Report month</span>
						<input
							type="month"
							value={month}
							onChange={(event) => setMonth(event.target.value)}
							max={currentMonth()}
						/>
					</label>
				</header>

				{error && (
					<div className="dashboard-error" role="alert">
						<span>{error}</span>
						<button onClick={loadDashboard}>Retry</button>
					</div>
				)}

				{loading ? (
					<div className="dashboard-loading">Loading your finances...</div>
				) : dashboard ? (
					<>
						<section className="summary-grid">
							<article className="summary-card income-card">
								<div className="summary-top">
									<span>Total income</span>
									<span className="summary-icon">↗</span>
								</div>
								<h2>{money(income)}</h2>
								<p>Recorded income this month</p>
							</article>

							<article className="summary-card expense-card">
								<div className="summary-top">
									<span>Total expenses</span>
									<span className="summary-icon">↘</span>
								</div>
								<h2>{money(expenses)}</h2>
								<p>Recorded spending this month</p>
							</article>

							<article className="summary-card balance-card">
								<div className="summary-top">
									<span>Remaining balance</span>
									<span className="summary-icon">₹</span>
								</div>
								<h2>{money(balance)}</h2>
								<p>Income minus expenses</p>
							</article>
						</section>

						<section className="dashboard-panels">
							<article className="panel">
								<div className="panel-heading">
									<div>
										<h2>Income vs. expenses</h2>
										<p className="muted">Monthly comparison</p>
									</div>
								</div>

								<div className="chart-legend">
									<span><i className="legend-income" /> Income</span>
									<span><i className="legend-expense" /> Expenses</span>
								</div>

								<div className="comparison-chart">
									<div className="chart-row">
										<span>Income</span>
										<div className="chart-track">
											<div
												className="chart-bar bar-income"
												style={{
													width: `${(income / maxAmount) * 100}%`,
												}}
											/>
										</div>
										<strong>{money(income)}</strong>
									</div>

									<div className="chart-row">
										<span>Expenses</span>
										<div className="chart-track">
											<div
												className="chart-bar bar-expense"
												style={{
													width: `${(expenses / maxAmount) * 100}%`,
												}}
											/>
										</div>
										<strong>{money(expenses)}</strong>
									</div>
								</div>

								{income === 0 && expenses === 0 && (
									<p className="empty-hint">
										No transactions recorded for this month yet.
									</p>
								)}
							</article>

							<article className="panel">
								<div className="panel-heading">
									<div>
										<h2>Financial summary</h2>
										<p className="muted">Your selected month</p>
									</div>
								</div>

								<div className="financial-row">
									<span>Income</span>
									<strong className="positive">{money(income)}</strong>
								</div>
								<div className="financial-row">
									<span>Expenses</span>
									<strong className="negative">{money(expenses)}</strong>
								</div>
								<div className="financial-row total-row">
									<span>Net balance</span>
									<strong>{money(balance)}</strong>
								</div>

								<p className="summary-note">
									This balance reflects recorded transactions, not a verified
									bank account balance.
								</p>
							</article>
						</section>

						<section className="panel category-breakdown-panel">
							<div className="panel-heading"><div><h2>Spending by category</h2><p className="muted">Expense totals for the selected month</p></div></div>
							{(dashboard.categoryBreakdown || []).length === 0 ? <p className="empty-hint">No expense categories recorded for this month yet.</p> : <div className="category-breakdown-list">{dashboard.categoryBreakdown.map((item) => { const value = Number(item.amount || 0); const max = Math.max(...dashboard.categoryBreakdown.map((row) => Number(row.amount || 0)), 1); return <div className="category-breakdown-row" key={item.category}><div><span>{item.category}</span><strong>{money(value)}</strong></div><div className="chart-track"><div className="chart-bar bar-expense" style={{ width: `${value / max * 100}%` }} /></div></div>; })}</div>}
						</section>

						<section className="panel transactions-panel">
							<div className="panel-heading">
								<div>
									<h2>Recent transactions</h2>
									<p className="muted">Your latest records for this month</p>
								</div>
								<Link className="text-link" to="/transactions">
									View all →
								</Link>
							</div>

							{dashboard.recentTransactions?.length > 0 ? (
								<div className="table-scroll">
									<table className="transaction-table">
										<thead>
											<tr>
												<th>Category</th>
												<th>Date</th>
												<th>Notes</th>
												<th>Type</th>
												<th className="amount-column">Amount</th>
											</tr>
										</thead>
										<tbody>
											{dashboard.recentTransactions.map((transaction) => (
												<tr key={transaction.id}>
													<td>
														<strong>{transaction.category}</strong>
													</td>
													<td>{displayDate(transaction.date)}</td>
													<td>{transaction.notes || "—"}</td>
													<td>
														<span
															className={`type-pill ${transaction.type === "INCOME"
																	? "type-income"
																	: "type-expense"
																}`}
														>
															{transaction.type}
														</span>
													</td>
													<td
														className={`amount-column ${transaction.type === "INCOME"
																? "positive"
																: "negative"
															}`}
													>
														{transaction.type === "INCOME" ? "+" : "−"}
														{money(transaction.amount)}
													</td>
												</tr>
											))}
										</tbody>
									</table>
								</div>
							) : (
								<div className="empty-state">
									<div className="empty-icon">₹</div>
									<h3>No transactions yet</h3>
									<p>
										Add income or expenses to see your financial activity here.
									</p>
									<Link className="primary-link" to="/transactions">
										Manage transactions
									</Link>
								</div>
							)}
						</section>
					</>
				) : null}
			</main>
		</div>
	);
}