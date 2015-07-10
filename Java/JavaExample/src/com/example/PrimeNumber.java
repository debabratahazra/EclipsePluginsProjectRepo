package com.example;
public class PrimeNumber {
	
	private static final int NUMBER = 31;
	private static final int LIMIT_NUMBER = 50;
     
    public static void main(String a[]){
        boolean prime = isPrimeNumber(NUMBER);
        if(prime) {
        	System.out.println("This " + NUMBER + " number is prime number");
        }
        else {
        	System.out.println("This " + NUMBER + " numnber is not a prime number");
        }
        
        showAllPrimeNumber(LIMIT_NUMBER);
    }
    
    /**
     * Verify the given number is prime or not
     * @param number
     * @return boolean
     */
    private static boolean isPrimeNumber(int number){
        for(int i=2; i<=number/2; i++){
            if(number % i == 0){
                return false;
            }
        }
        return true;
    }
    
    /**
     * Show all the prime number between 1 to given number
     * @param uptoDigit
     */
    private static void showAllPrimeNumber (int uptoDigit){
    	if(uptoDigit < 0) {
    		return;
    	}
    	System.out.println("The all Prime numbers from 1 to " + LIMIT_NUMBER + " are : ");
    	for(int i=1; i<=uptoDigit; i++) {
    		if(isPrimeNumber(i)){
    			System.out.print(i + ", ");
    		}
    	}
    }
}
