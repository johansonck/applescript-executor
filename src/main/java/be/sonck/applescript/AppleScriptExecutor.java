package be.sonck.applescript;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class AppleScriptExecutor {
	public String execute(File script, String... args) throws AppleScriptException {
		try {
			Process process = Runtime.getRuntime().exec(createCommand(script, args));
			
			String stdout = IOUtils.toString(process.getInputStream(), "UTF-8");
			String stderr = IOUtils.toString(process.getErrorStream(), "UTF-8");

			if (process.waitFor() != 0) {
				// if something bad happened while trying to run the script, throw an
				// AppleScriptException letting the user know what the problem was
				throw new AppleScriptException(stderr);
			} else {
				return stdout;
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	private String[] createCommand(File script, String... args) {
		List<String> cmd = new ArrayList<String>();
		cmd.add("/usr/bin/osascript");
		
		// add necessary command-line switches here...
		cmd.add("-s");
		cmd.add("s");  // print values in recompilable source form
		
		cmd.add(script.getPath());
		
		for (int i = 0 ; i < args.length; i++) {
			cmd.add(args[i]);
		}
		
		return (String[]) cmd.toArray(new String[0]);
	}
}
