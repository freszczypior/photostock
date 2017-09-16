package pl.com.bottega.photostock.sales.ui;

import java.util.Scanner;

public class AuthenticatiionScreen{

    private Scanner scanner;
    private AuthenticationManager authenticationManager;

    public AuthenticatiionScreen(AuthenticationManager authenticationManager, Scanner scanner) {
        this.scanner = scanner;
        this.authenticationManager = authenticationManager;

    }

    public void show(){
        System.out.println("Podaj login: ");
        String login = scanner.nextLine();
        if (authenticationManager.authenticate(login))
            return;
        System.out.println("Nieprawidłowy login");
    }

}

