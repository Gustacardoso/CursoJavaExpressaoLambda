package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import entities.Product;

public class Program {

	public static void main(String[] args) {
		
		Locale.setDefault(Locale.US);
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Enter full file path");
		String path = sc.nextLine();
		//lendo  o caminho do  arquivo
		
		try(BufferedReader br = new BufferedReader(new FileReader(path))){
			List<Product> list = new ArrayList<>();
			
			String line = br.readLine();//variavel para ler as linhas
			while (line != null) {
				String[] fields = line.split(",");//vetor de string, que separa ate o parametro  estabelecido
				                                //que nesse caso e a virgula
				//split recorta string com passe na virgula 
				list.add(new Product(fields[0], Double.parseDouble(fields[1])));//adicionando  nas prosições que froam expecificadas
				line = br.readLine();//mandando  ler a poxima linha
			}
			//vamos mostrar o preço medio de todos os produtos
			//vamos fazer um pepiline
			double avg = list.stream()
					    .map(p -> p.getPrice())//para cada produto p da minha lista eu  quero pegar o getprice
					                //porque so precisamos nesse caso do price
					    .reduce(0.0, (x, y) -> x+y)/ list.size()//permite fazer o somatorio dos preços
			                      //x e y  elevando  a soma de x e y dividida pelo tamanho da list
					    ;
			System.out.println("Average price "+ String.format("%.2f", avg));
			
			Comparator<String> comp = (s1 , s2 ) -> s1.toUpperCase().compareTo(s2.toUpperCase());
			
			List<String> names = list.stream()
					.filter(p -> p.getPrice() < avg)//filtrando  por preco inferior a media
					.map(p-> p.getName()).sorted(comp.reversed())//pegando  o nome e colocado em ordem decrecente
					.collect(Collectors.toList());
			
			names.forEach(System.out::println);
		}catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		
		sc.close();
	}

}
