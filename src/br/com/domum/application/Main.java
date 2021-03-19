package br.com.domum.application;

import br.com.domum.entities.Product;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter full file path: ");
        String fullFileName = sc.nextLine();

        try(BufferedReader br = new BufferedReader(new FileReader(fullFileName))){
            List<Product> products = new ArrayList<>();

            String line = br.readLine();
            while (line != null){
                String[] fields = line.split(",");
                products.add(new Product(fields[0], Double.parseDouble(fields[1])));
                line = br.readLine();
            }

            Double averagePrice = products.stream()
                    .map(p -> p.getPrice())
                    .reduce(0.0, (x, y) -> x + y) / products.size();

            System.out.printf("Average price: %.2f%n", averagePrice);

            Comparator<String> comp = (s1, s2) -> s1.toUpperCase().compareTo(s2.toUpperCase());

            List<String> names = products.stream()
                    .filter(p -> p.getPrice() < averagePrice)
                    .map(x -> x.getName())
                    .sorted(comp.reversed())
                    .collect(Collectors.toList());

            names.forEach(System.out::println);

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        sc.close();
    }
}
