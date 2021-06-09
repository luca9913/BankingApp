package Konto;

public class Kreditkarte extends Konto{
    double debit;
    public Kreditkarte(){
        debit = 0;
    }
    //Bank bezahlen --> debit auf 0
    private void payBank(double amount){
        debit -= amount;
    }
    //Sachen kaufen --> debit auf x
    private void buyStuff(double amount){
        debit += amount;
    }
}
