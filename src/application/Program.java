package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import entities.Sale;

public class Program {

	public static void main(String[] args) {

		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Enter the file path: ");
		String path = sc.next();
		
		try(BufferedReader br = new BufferedReader(new FileReader(path))){
			
			List<Sale> sale = new ArrayList<>();
			
			String line = br.readLine();
			while(line != null) {
				
				String[] fields = line.split(",");
				Integer month = Integer.parseInt(fields[0]);
				Integer year = Integer.parseInt(fields[1]);
				String seller = fields[2];
				Integer items = Integer.parseInt(fields[3]);
				Double total = Double.parseDouble(fields[4]);
				
				sale.add(new Sale(month, year, seller, items, total));
				
				line = br.readLine();
			}
			
			Comparator<Sale> comp = (s1, s2) -> s1.averagePrice().compareTo(s2.averagePrice());
			
			List<Sale> firstFive = sale.stream()
					.filter(s -> s.getYear() == 2016)
					.sorted(comp.reversed())
					.limit(5)
					.toList();
			
			List<Sale> logan = sale.stream()
					.filter(l -> l.getSeller().equals("Logan"))
					.toList();
				
			Double total1 = logan.stream()
					.filter(l -> l.getMonth() == 1)
					.map(l -> l.getTotal())
					.reduce(0.0, (x, y) -> x + y);
			
			Double total2 = logan.stream()
					.filter(l -> l.getMonth() == 7)
					.map(l -> l.getTotal())
					.reduce(total1, (x, y) -> x + y);
					
			System.out.println();
			System.out.println("Cinco primeiras vendas de 2016 de maior preço médio:");

			firstFive.forEach(System.out::println);
				
			System.out.println();
			System.out.println("Valor total vendido pelo vendedor Logan nos meses 1 e 7 = " + String.format("%.2f", total2));
		
		}catch(IOException e) {
			System.out.println("Error: " + path + " (O sistema não pode encontrar o arquivo especificado)");
		}
		
		sc.close();
	}

}
