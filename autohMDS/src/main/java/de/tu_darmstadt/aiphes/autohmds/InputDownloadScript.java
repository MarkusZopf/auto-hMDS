package de.tu_darmstadt.aiphes.autohmds;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.FileSystems;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.chrome.ChromeDriver;

import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.extractors.KeepEverythingExtractor;

public class InputDownloadScript {
	public static void main(String[] args) throws Exception {
		System.setProperty("webdriver.chrome.driver", FileSystems.getDefault().getPath("res/chromedriver.exe").toString());

		String[] languages = { "en", "de" };
		for (String language : languages) {
			String path = args[0] + "auto-hMDS-" + language;

			getAllDirectories(path).forEach(topicfolder -> {
				downloadInputs(path + "\\" + topicfolder + "\\input");
			});
		}
	}

	public static void downloadInputs(String inputfolder) {
		ChromeDriver chromeDriver = new ChromeDriver();
		chromeDriver.manage().timeouts().pageLoadTimeout(1, TimeUnit.MINUTES);

		try {
			String sentenceURLs = getTextFileContent(inputfolder + "\\collected_sentence_ids.txt");

			for (String sentenceURL : sentenceURLs.split("\n")) {
				int sentenceid = Integer.parseInt(sentenceURL.split("\t")[0]);
				int urlindex = Integer.parseInt(sentenceURL.split("\t")[1]);
				String outputFilename = inputfolder + "\\sentence_" + sentenceid + "_" + urlindex;

				String url = sentenceURL.split("\t")[2];

				// download HTML code:
				if (!(new File(outputFilename + ".html")).exists()) {
					chromeDriver.get(url);
					String htmlDocument = chromeDriver.getPageSource();
					writeTextFileContent(outputFilename + ".html", htmlDocument);
				}

				// apply KeepEverythingExtractor to HTML code:
				if (!(new File(outputFilename + ".html.ke.txt")).exists()) {
					try {
						String htmlDocument = getTextFileContent(outputFilename + ".html");
						String keepEverythingExtractor = KeepEverythingExtractor.INSTANCE.getText(htmlDocument);
						keepEverythingExtractor = Arrays.asList((keepEverythingExtractor.split("\n"))).stream().filter(s -> s.length() >= 50).reduce("", (a, b) -> a + "\n" + b).trim();
						writeTextFileContent(outputFilename + ".html.ke.txt", keepEverythingExtractor);
					}

					catch (BoilerpipeProcessingException e) {
						System.err.println("Could not extract text for file '" + outputFilename + ".html'.");
					}
				}
			}
		}

		catch (IOException e) {
			System.err.println("Coud not read file '" + inputfolder + "\\collected_sentence_ids.txt'.");
		}

		chromeDriver.quit();
	}

	public static List<String> getAllDirectories(String path) {
		return Arrays.asList(new File(path).list(new FilenameFilter() {
			@Override
			public boolean accept(File current, String name) {
				return new File(current, name).isDirectory();
			}
		}));
	}

	public static void writeTextFileContent(String filename, String content) {
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(filename), false), "UTF-8"))) {
			writer.write(content);
			writer.close();
		}

		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getTextFileContent(String filename) throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filename)), "UTF-8"));
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			stringBuilder.append(line.toString() + "\n");
		}

		bufferedReader.close();
		return stringBuilder.toString().trim();
	}
}
