package com.home.designspatterns.structural;

import java.io.IOException;

public class Proxy {

	public static void main(String[] args) {
		CommandExecutor commandExecutor = new CommandExecutorProxy("adminsss", "pwdsss");
		commandExecutor.runCommand("ls -ltr");
		commandExecutor.runCommand("rm -rf abc.txt");

	}

}

interface CommandExecutor {

	public void runCommand(String cmd);

}

class CommandExecutorImpl implements CommandExecutor {

	@Override
	public void runCommand(String cmd) {
		try {
			Runtime.getRuntime().exec(cmd);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(cmd + " command is executed successfully.");

	}

}

class CommandExecutorProxy implements CommandExecutor {
	private boolean isAdmin;
	private CommandExecutor commandExecutor;

	public CommandExecutorProxy(String name, String pwd) {

		if ("admin".equalsIgnoreCase(name) && "pwd".equalsIgnoreCase(pwd)) {
			isAdmin = true;
		}
		commandExecutor = new CommandExecutorImpl();
	}

	@Override
	public void runCommand(String cmd) {
		if (isAdmin) {
			commandExecutor.runCommand(cmd);
		} else {

			if (cmd.trim().startsWith("rm")) {
				System.out.println(cmd + " command can't be executed.");
			} else {
				commandExecutor.runCommand(cmd);
			}
		}

	}

}
