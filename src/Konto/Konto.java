package Konto;

import Person.*;

public abstract class Konto{
    public String id;
    public String type;
    public double balance;
    public double dispo;
    public double transferlimit;
    public Person owner;
    public Person banker;

}