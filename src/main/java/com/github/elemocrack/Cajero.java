package com.github.elemocrack;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Cajero {
    private List<User> users = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    private int attempts = 2;
    private boolean logged = false;
    private int loggedId = 0;

    public Cajero() {
        defaultAccounts();
    }

    public void prenderCajero() {
        while (true) {
            System.out.println("1 Registrarse \n2 Iniciar sesion");
            if (scanner.hasNextInt()) {
                int options = scanner.nextInt();
                switch (options) {
                    case 1:
                        System.out.println("Ingrese su nombre");
                        String inputName = scanner.next();
                        if (existAccount(inputName)) {
                            System.out.println("Esta cuenta ya esta registrada");
                            break;
                        }
                        System.out.println("Ingrese su contraseña");
                        String inputPassword = scanner.next();
                        System.out.println("Ingrese el dinero a depositar por primera vez");
                        if (scanner.hasNextDouble()) {
                            double initialBalance = scanner.nextDouble();
                            newUser(inputName, inputPassword, initialBalance);
                        } else {
                            System.out.println("Tiene que ingresar un monto numerico");
                            scanner.nextLine();
                        }
                        break;
                    case 2:
                        sesion:
                        while (true) {
                            if (!logged) {
                                System.out.println("Ingrese su nombre");
                                String logginName = scanner.next();
                                System.out.println("Ingrese su contraseña");
                                String logginPassword = scanner.next();
                                if (attempts > 0) {
                                    for (int i = 0; i < users.size(); i++) {
                                        User user = users.get(i);
                                        if (user != null) {
                                            if (user.getName().equals(logginName) && user.getPassword().equals(logginPassword)) {
                                                System.out.println("Nombre y contraseña correctos");
                                                logged = true;
                                                loggedId = user.getUserId();
                                                break;
                                            }
                                        }
                                    }
                                    if (!logged) {
                                        System.out.println("Nombre o contraseña invalidos");
                                    }
                                } else {
                                    System.out.println("Te quedaste sin intentos");
                                    attempts = 2;
                                    break;
                                }
                                attempts--;
                            } else {
                                User user = findUserById(loggedId);
                                System.out.println("-----------------\n1 Consultar saldo \n2 Ingresar dinero \n3 Retirar dinero \n4 Depositar a otra cuenta \n5 Ver id de usuarios \n6 Cerrar sesion");
                                int option = scanner.nextInt();
                                switch (option) {
                                    case 1:
                                        System.out.println(showBalance(user));
                                        break;
                                    case 2:
                                        System.out.println("Ingrese la cantidad a depositar");
                                        if (scanner.hasNextDouble()) {
                                            double newDepositBalance = scanner.nextDouble();
                                            addBalance(newDepositBalance, user);
                                        } else {
                                            System.out.println("Tiene que ingresar montos numericos");
                                            break;
                                        }
                                        break;
                                    case 3:
                                        System.out.println("Ingrese la cantidad de dinero a retirar");
                                        if (scanner.hasNextDouble()) {
                                            double withdrawBalance = scanner.nextDouble();
                                            if (user.getBalance() < withdrawBalance) {
                                                System.out.println("No tienes tanto dinero");
                                                break;
                                            } else {
                                                withdrawBalance(withdrawBalance, user);
                                            }
                                        } else {
                                            System.out.println("Tienes que ingresar montos numericos");
                                            break;
                                        }
                                        break;
                                    case 4:
                                        System.out.println("Ingrese la id del usuario a transferir");
                                        if (scanner.hasNextInt()) {
                                            int userToTransferId = scanner.nextInt();
                                            User userToTransfer = findUserById(userToTransferId);
                                            if (userToTransfer != null) {
                                                System.out.println("Ingrese la cantidad a depositar");
                                                if (scanner.hasNextDouble()) {
                                                    double moneyToTransfer = scanner.nextDouble();
                                                    if (user.getBalance() < moneyToTransfer) {
                                                        System.out.println("No tienes suficiente dinero");
                                                        break;
                                                    } else {
                                                        moneyTransfer(user, userToTransfer, moneyToTransfer);
                                                        System.out.println("Transferencia exitosa \nAhora su saldo es de " + user.getBalance());
                                                    }
                                                } else {
                                                    System.out.println("Tiene que ingresar un monto numerico");
                                                }
                                            } else {
                                                System.out.println("Ese usuario no existe");
                                            }
                                        } else {
                                            System.out.println("Tiene que ingresar un monto numerico");
                                        }
                                        break;
                                    case 5:
                                        getUsersId();
                                        break;
                                    case 6:
                                        logged = false;
                                        break sesion;
                                    case 7:
                                        System.out.println("Escriba el nombre de la cuenta a borrar");
                                        String accounToDelete = scanner.next();
                                        if (existAccount(accounToDelete)) {
                                            deleteUser(accounToDelete);
                                            System.out.println("Cuenta eliminada con exito");
                                        } else {
                                            System.out.println("Esa cuenta no existe");
                                        }
                                        break;
                                    default:
                                        System.out.println("No es opcion valida");
                                        break;
                                }
                            }
                        }
                        break;
                    default:
                        System.out.println("No es opcion valida");
                        break;
                }
            } else {
                System.out.println("Las opciones son numericas");
                scanner.nextLine();
            }
        }
    }

    public void newUser(String name, String password, double balance) {
        User newUser = new User();
        newUser.setName(name);
        newUser.setPassword(password);
        newUser.setBalance(balance);
        users.add(newUser);
    }

    public void defaultAccounts() {
        newUser("Juancito", "asd123", 250);
        newUser("Alvarito", "asd123", 1000);
        newUser("Lorem", "ipsum", 5000);
    }

    public void findAccounts() {
        //metodo para debug de codigo
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            System.out.println("Nombre: " + user.getName() + "\nContraseña: " + user.getPassword() + "\nDinero: " + user.getBalance());
            System.out.println(user.getUserId());
        }
    }

    public double showBalance(User user) {
        return user.getBalance();
    }

    public void addBalance(double newBalance, User user) {
        user.setBalance(user.getBalance() + newBalance);
    }

    public void withdrawBalance(double withdrawBalance, User user) {
        user.setBalance(user.getBalance() - withdrawBalance);
    }

    public void moneyTransfer(User user, User userToTransfer, double moneyToTransfer) {
        user.setBalance(user.getBalance() - moneyToTransfer);
        userToTransfer.setBalance(userToTransfer.getBalance() + moneyToTransfer);
    }

    public void getUsersId() {
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            System.out.println("Id: " + user.getUserId() + " Nombre: " + user.getName());
        }
    }

    public User findUserById(int userId) {
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (userId == user.getUserId()) {
                return user;
            }
        }
        return null;
    }

    public boolean existAccount(String userName) {
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (userName.equals(user.getName())) {
                return true;
            }
        }
        return false;
    }

    public void deleteUser(String userName) {
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (user.getName().equals(userName)) {
                users.remove(user);
                break;
            }
        }

    }

    public User findUserByName(String userName) {
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (user.getName().equals(userName)) {
                return user;
            }
        }
        return null;
    }
}
