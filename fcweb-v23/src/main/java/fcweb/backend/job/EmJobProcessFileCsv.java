package fcweb.backend.job;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;

@Controller
public class EmJobProcessFileCsv{

	private final static Log LOG = LogFactory.getLog(EmJobProcessFileCsv.class);

	final static int size = 1024;

	public void downloadCsv(String http_url, String path_csv, String fileName, int headCount) throws Exception {
		try {
			LOG.debug(http_url);
			fileDownload(http_url, fileName + ".html", path_csv);
		} catch (Exception ex) {
			LOG.error(ex.getMessage());
//			File f = new File(path_csv + fileName + ".html");
//			if (f.exists()) {
//				f.deleteOnExit();
//			}
		}

		File input = new File(path_csv + fileName + ".html");
		Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");

		// select all <tr> or Table Row Elements
		Elements tableRows = doc.select("table");

		String data = "";
		// Load ArrayList with table row strings
		for (Element tableRow : tableRows) {

			Elements trRows = tableRow.select("tr");
			int conta = 0;
			for (Element trRow : trRows) {
				conta++;
				if (conta > headCount) {
					Elements tdRows = trRow.select("td");
					for (Element tdRow : tdRows) {
						String rowData = tdRow.text();
						if ( StringUtils.isEmpty(rowData) ) {
							Elements img = tdRow.select("img");
							rowData = img.attr("title");
							if ( StringUtils.isEmpty(rowData) ) {
								rowData = img.attr("alt");
							}
						}
						//LOG.debug(rowData);
						data += rowData + ";";
					}
					data += "\n";
				}
			}
		}
		
		FileOutputStream outputStream = null;
		try {
			
			// DELETE
			File f = new File(path_csv + fileName + ".csv");
			if (f.exists()) {
				f.delete();
			}
		    outputStream = new FileOutputStream(path_csv + fileName + ".csv");
		    byte[] strToBytes = data.getBytes();
		    outputStream.write(strToBytes);

		} catch (Exception e) {
			LOG.error(e.getMessage());
		} finally {
			if (outputStream != null) {
				outputStream.close();
			}
		}

	    /*
		BufferedWriter output = null;
		try {
			// Create temp file.
			//File temp = File.createTempFile("temp_" + fileName, ".csv");
			// Delete temp file when program exits.
			//temp.deleteOnExit();

			
			// Write to temp file
			output = new BufferedWriter(new FileWriter(temp));
			output.write(data);
			if (output != null) {
				output.close();
			}

			// DELETE
			File f = new File(path_csv + fileName + ".csv");
			if (f.exists()) {
				f.delete();
			}

			// Destination directory
			// File dir = new File(path_csv);
			// create the destination file object
			File dest = new File(path_csv + fileName + ".csv");

			// Move file to new directory
			boolean success = temp.renameTo(dest);
			if (!success) {
				// File was not successfully moved
				LOG.error("File was not successfully moved " + success);
			}

		} catch (Exception e) {
			LOG.error(e.getMessage());
		} finally {
			if (output != null) {
				output.close();
			}
		}
		*/

	}


	public void downloadCsvOLD(String http_url, String path_csv, String fileName,
			int giornata) throws Exception {
		// String fileName = "voti" + giornata + ".html";
		try {
			LOG.debug(http_url);
			fileDownload(http_url, fileName + ".html", path_csv);
		} catch (Exception ex) {
			LOG.error(ex.getMessage());
//			File f = new File(path_csv + fileName);
//			if (f.exists()) {
//				f.deleteOnExit();
//			}
		}

		String charsetName = "ISO-8859-1";
		charsetName = "UTF-8";
		File input = new File(path_csv + fileName + ".html");
		Document doc = Jsoup.parse(input, charsetName, "http://example.com/");

		// select all <tr> or Table Row Elements
		Elements tableRows = doc.select("table");

		String data = "ID_GIORNATA;SQUADRA;COGN_GIOCATORE;ID_GIOCATORE;VOTO_GIOCATORE;GOAL_REALIZZATO;GOAL_SUBITO;AMMONIZIONE;ESPULSIONE;RIGORE_SEGNATO;RIGORE_FALLITO;RIGORE_PARATO;AUTORETE;ASSIST;GDV;GDP";
		data += "\n";

		// Load ArrayList with table row strings
		for (Element tableRow : tableRows) {

			Elements trRows = tableRow.select("tr");
			int conta = 0;
			for (Element trRow : trRows) {
				conta++;
				if (conta > 4) {
					Elements tdRows = trRow.select("td");
					if (tdRows.size() > 10) {

						data += giornata + ";";
						data += tdRows.get(4).text().trim() + ";";
						data += tdRows.get(1).text().trim() + ";";
						data += giornata + ";";
						String voto = tdRows.get(6).text().trim();
						voto = StringUtils.replace(voto, ",", ".");
						data += voto + ";";

						// GOAL_REALIZZATO
						try {
							data += Integer.parseInt(tdRows.get(7).text()) + ";";
						} catch (Exception e) {
							data += 0 + ";";
						}

						// GOAL_SUBITO
						try {
							data += Integer.parseInt(tdRows.get(8).text()) + ";";
						} catch (Exception e) {
							data += 0 + ";";
						}

						try {
							data += Integer.parseInt(tdRows.get(23).text()) + ";";
						} catch (Exception e) {
							data += 0 + ";";
						}

						try {
							data += Integer.parseInt(tdRows.get(24).text()) + ";";
						} catch (Exception e) {
							data += 0 + ";";
						}
						// RIGORE_SEGNATO;;
						try {
							data += Integer.parseInt(tdRows.get(29).text()) + ";";
						} catch (Exception e) {
							data += 0 + ";";
						}
						// RIGORE_FALLITO
						try {
							data += Integer.parseInt(tdRows.get(27).text()) + ";";

						} catch (Exception e) {
							data += 0 + ";";
						}
						// RIGORE_PARATO
						try {
							data += Integer.parseInt(tdRows.get(28).text()) + ";";

						} catch (Exception e) {
							data += 0 + ";";
						}

						try {
							data += Integer.parseInt(tdRows.get(9).text()) + ";";
						} catch (Exception e) {
							data += 0 + ";";
						}

						try {
							data += Integer.parseInt(tdRows.get(10).text()) + ";";
						} catch (Exception e) {
							data += 0 + ";";
						}

						try {
							data += Integer.parseInt(tdRows.get(25).text()) + ";";
						} catch (Exception e) {
							data += 0 + ";";
						}

						try {
							data += Integer.parseInt(tdRows.get(26).text()) + ";";
						} catch (Exception e) {
							data += 0 + ";";
						}

						data += "\n";
					}
				}
			}
		}
		
		
		FileOutputStream outputStream = null;
		try {
			
			// DELETE
			File f = new File(path_csv + fileName + ".csv");
			if (f.exists()) {
				f.delete();
			}
		    outputStream = new FileOutputStream(path_csv + fileName + ".csv");
		    byte[] strToBytes = data.getBytes();
		    outputStream.write(strToBytes);

		} catch (Exception e) {
			LOG.error(e.getMessage());
		} finally {
			if (outputStream != null) {
				outputStream.close();
			}
		}
		
		/*
		BufferedWriter output = null;
		try {
			// Create temp file.
			File temp = File.createTempFile("temp_" + fileName, ".csv");
			// Delete temp file when program exits.
			// temp.deleteOnExit();

			// Write to temp file
			output = new BufferedWriter(new FileWriter(temp));
			output.write(data);
			output.close();

			// DELETE
			File f = new File(path_csv + fileName + ".csv");
			if (f.exists()) {
				f.delete();
			}

			File dest = new File(path_csv + fileName + ".csv");
			// Move file to new directory
			boolean success = temp.renameTo(dest);
			if (!success) {
				// File was not successfully moved
				LOG.error("File was not successfully moved " + success);
			}

		} catch (IOException e) {
			LOG.error(e.getMessage());
		} finally {
			if (output != null) {
				output.close();
			}
		}
		*/
	}

	public void fileDownload(String fAddress, String localFileName,
			String destinationDir) throws Exception {

		// Create a new trust manager that trust all certificates
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager(){
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(
					java.security.cert.X509Certificate[] certs,
					String authType) {
			}

			public void checkServerTrusted(
					java.security.cert.X509Certificate[] certs,
					String authType) {
			}
		} };

		// Activate the new trust manager
		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
		}

		OutputStream outStream = null;
		URLConnection uCon = null;
		InputStream is = null;
		try {
			URL Url;
			byte[] buf;
			int ByteRead,ByteWritten = 0;
			Url = new URL(fAddress);
			outStream = new BufferedOutputStream(new FileOutputStream(destinationDir + localFileName));

			uCon = Url.openConnection();
			is = uCon.getInputStream();
			buf = new byte[size];
			while ((ByteRead = is.read(buf)) != -1) {
				outStream.write(buf, 0, ByteRead);
				ByteWritten += ByteRead;
			}
			LOG.info("Downloaded Successfully.");
			LOG.debug("File name:\"" + localFileName + "\"\nNo ofbytes :" + ByteWritten);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				is.close();
			}
			if (outStream != null) {
				outStream.close();
			}
		}
	}

}
