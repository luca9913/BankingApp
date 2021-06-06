package Konto;

import Person.*;

abstract class Konto{
    private String type;
    private double balance;
    private double dispo;
    private double transfer_limit;
    private Person owner;
    private Person banker;

}