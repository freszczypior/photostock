package pl.com.bottega.photostock.sales.ui;

import java.util.Scanner;

public class AuthenticatiionScreen {

private AuthenticationManager authenticationManager;
private Scanner scanner;

    public AuthenticatiionScreen(AuthenticationManager authenticationManager, Scanner scanner) {
        this.authenticationManager = authenticationManager;
        this.scanner = scanner;
    }

    public void show(){
        System.out.println("Podaj login: ");
        String login = scanner.nextLine();
        if (authenticationManager.authenticate(login))
            return;
        System.out.println("Nieprawidłowy login");
    }

}

