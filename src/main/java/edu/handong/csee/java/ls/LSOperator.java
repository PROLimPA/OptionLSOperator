package edu.handong.csee.java.ls;

import java.io.File;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class LSOperator {
	
	boolean aOption;
	boolean dOption;
	boolean FOption;
	boolean tOption;
	boolean ROption;
	boolean help;
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		
		LSOperator lsOperator = new LSOperator();
		lsOperator.run(args);
	}
	
	public void run(String[] args) {
		Options options = createOptions();
		
		if(args.length == 1 && args[0].equals("ls")) {
			File file = new File(".");
			listFilesInDirectory(file);
		}
		
		else if(args[0].equals("ls") && parseOptions(options, args)) {
			if(help) {
				printHelp(options);
				return;
			}
			
			if(aOption) {
				File file = new File(".");
				File parent = new File("..");
									
				if(file.exists() && file.isDirectory()) {
					System.out.println(file.getName());
					if(parent.exists() && parent.isDirectory())
						System.out.println(parent.getName());
					
					listFilesInDirectory(file);
				}
			}
			
			if(dOption) {
				File file = new File(".");
				if(file.exists() && file.isDirectory()) {
					File[] files = file.listFiles();
					for(int i = 0; i < files.length; i++) {
						if(files[i].isDirectory()) {
							System.out.println(files[i].getName() + "/");
						}
					}
				}
			}
			
			if(FOption) {
				File file = new File(".");
				if(file.exists() && file.isDirectory()) {
					File[] files = file.listFiles();
					for(int i = 0; i < files.length; i++) {
						if(files[i].isDirectory()) 
							System.out.println(files[i].getName() + "/");
						else if(files[i].canExecute())
							System.out.println(files[i].getName() + "*");
						else
							System.out.println(files[i].getName());
					}
				}
			}
			
			if(tOption) {
				File file = new File(".");
				if(file.exists() && file.isDirectory()) {
					File[] files = file.listFiles();
					for(int i = 0; i < files.length; i++) {
						for(int j = 1; j < files.length; j++) {
							if(files[i].lastModified() < files[j].lastModified()) {
								File temp = files[i];
								files[i] = files[j];
								files[j] = temp;
							}
						}
					}
					for(int i = 0; i < files.length; i++) {
						System.out.println(files[i].getName());
					}
				}
			}
			
			if(ROption) {
				File file = new File(".");
				if(file.exists() && file.isDirectory()) {
					listFilesInDirectory(file);
					System.out.println("\n");
						
					File[] files = file.listFiles();
					for(int i = 0; i < files.length; i++) {
						if(files[i].isDirectory()) {
							System.out.println(files[i].getName() + " is Directory");
							listFilesInDirectory(files[i]);
							System.out.println("");
						}
					}
				}
			}
			
		}
	}
	
	private void listFilesInDirectory(File file) {
		File[] files = file.listFiles();
		for(int i = 0; i < files.length; i++) {
			System.out.println(files[i].getName());
		}
	}
	
	private Options createOptions() {
		Options options = new Options();
		
		options.addOption(Option.builder("a").longOpt("option a")
				.desc("List all items in directory, including those which starts with point(.)")
				.build());
		
		options.addOption(Option.builder("d").longOpt("option d")
				.desc("shows information about a symbolic link or directory")
				.build());
		
		options.addOption(Option.builder("F").longOpt("option F")
				.desc("appends a character revealing the nature of a file")
				.build());
		
		options.addOption(Option.builder("t").longOpt("option t")
				.desc("sort the list of files by modification time.")
				.build());
		
		options.addOption(Option.builder("R").longOpt("option R")
				.desc("recursively lists subdirectories.")
				.build());
		
		options.addOption(Option.builder("h").longOpt("help")
				.desc("show a Help page")
				.build());
		
		return options;
	}
	
	private boolean parseOptions(Options options, String[] args) {
		CommandLineParser parser = new DefaultParser();
		
		try {
			CommandLine cmd = parser.parse(options, args);
			
			aOption = cmd.hasOption("a");
			dOption = cmd.hasOption("d");
			FOption = cmd.hasOption("F");
			tOption = cmd.hasOption("t");
			ROption = cmd.hasOption("R");
			help = cmd.hasOption("h");
			
		}catch (Exception e) {
			printHelp(options);
			return false;
		}
		
		return true;
	}
	
	private void printHelp(Options options) {
		HelpFormatter formatter = new HelpFormatter();
		String header = "LSOperator";
		String footer = "";
		formatter.printHelp("OptionLSOperator", header, options, footer, true);
	}
}
