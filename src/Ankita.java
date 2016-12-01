/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author acer
 */
class getDofS {
	int Degree_of_separation = 0;
	int dest_found = 0;
	String Destination = "950";
	String Source = "320";
	String Source_filename;
	List<String> source_list = new ArrayList<String>();
	List<String> dest_list = new ArrayList<String>();
	int fromSource = 1;

	getDofS() {
		source_list.add(Source);
		dest_list.add(Destination);

	}

	public void FindDegree() {
		// int fromDest=0;
		while (dest_found != 1) {
			finduser();
			if (Degree_of_separation >= 325)
				break;

		}

		if (dest_found == 0) {
			System.out.println("Destination Not Found");
			System.out.println("path lenght exceeds the limit");

		} else
			System.out.println("Degree of separation: " + Degree_of_separation);

	}

	public void finduser() {
		if (fromSource == 1) {
			Source = source_list.get(source_list.size() - 1);
			Destination = dest_list.get(dest_list.size() - 1);
		} else {
			Source = dest_list.get(dest_list.size() - 1);
			Destination = source_list.get(source_list.size() - 1);
		}

		System.out.println(Source);
		System.out.println(Destination);
		List<String> following_of = new ArrayList<String>();
		Source_filename = "following_of_" + Source + ".txt";
		File file = new File(Source_filename);
		try (FileReader fis = new FileReader(file)) {
			BufferedReader bufferedReader = new BufferedReader(fis);
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line);
				stringBuffer.append("\n");
			}

			fis.close();
			String contents = stringBuffer.toString();
			contents = contents.replace("[", "");
			contents = contents.replace("]", "");
			for (String spfol : contents.split(",")) {
				following_of.add(spfol.trim());
			}

			for (String element : following_of) {

				if (fromSource == 1) {
					if (dest_list.contains(element)) {
						System.out.println("Destination Found");
						Degree_of_separation++;
						dest_found = 1;
						break;
					}
				} else {
					if (source_list.contains(element)) {
						System.out.println("Destination Found");
						Degree_of_separation++;
						dest_found = 1;
						break;
					}
				}
				// System.out.println(element);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (dest_found == 0) {

			List<Integer> following_no = new ArrayList<Integer>();
			for (String element : following_of) {
				Source_filename = "following_of_" + element + ".txt";
				file = new File(Source_filename);
				try (FileReader fis = new FileReader(file)) {
					BufferedReader bufferedReader = new BufferedReader(fis);
					StringBuffer stringBuffer = new StringBuffer();
					List<String> sub_following_of = new ArrayList<String>();
					String line;
					while ((line = bufferedReader.readLine()) != null) {
						stringBuffer.append(line);
						stringBuffer.append("\n");
					}

					fis.close();
					String contents = stringBuffer.toString();
					contents = contents.replace("[", "");
					contents = contents.replace("]", "");
					for (String spfol : contents.split(",")) {
						sub_following_of.add(spfol.trim());
					}
					following_no.add(sub_following_of.size());

				} catch (IOException e) {
					e.printStackTrace();
				}

			}
			int max = following_no.get(0);
			int max_index = 0;
			for (int i = 1; i < following_no.size(); i++) {
				// System.out.println(following_no.get(i));
				if (following_no.get(i) > max) {
					max = following_no.get(i);
					max_index = i;
				}
			}
			// System.out.println("max is :"+max);
			// System.out.println(following_of.get(max_index));
			if (fromSource == 1) {
				source_list.add(following_of.get(max_index));
				fromSource = 0;

			} else {
				dest_list.add(following_of.get(max_index));
				fromSource = 1;

			}
			Degree_of_separation++;

		}
	}
}
