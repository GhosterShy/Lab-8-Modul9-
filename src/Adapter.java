interface IPaymentProcessor {
    void processPayment(double amount);
    void refundPayment(double amount);
}

class InternalPaymentProcessor implements IPaymentProcessor {
    @Override
    public void processPayment(double amount) {
        System.out.println("Processing payment of " + amount + " via internal system.");
    }

    @Override
    public void refundPayment(double amount) {
        System.out.println("Refunding payment of " + amount + " via internal system.");
    }
}

class ExternalPaymentSystemA {
    public void makePayment(double amount) {
        System.out.println("Making payment of " + amount + " via External Payment System A.");
    }

    public void makeRefund(double amount) {
        System.out.println("Making refund of " + amount + " via External Payment System A.");
    }
}

class ExternalPaymentSystemB {
    public void sendPayment(double amount) {
        System.out.println("Sending payment of " + amount + " via External Payment System B.");
    }

    public void processRefund(double amount) {
        System.out.println("Processing refund of " + amount + " via External Payment System B.");
    }
}

class PaymentAdapterA implements IPaymentProcessor {
    private final ExternalPaymentSystemA externalSystemA;

    public PaymentAdapterA(ExternalPaymentSystemA externalSystemA) {
        this.externalSystemA = externalSystemA;
    }

    @Override
    public void processPayment(double amount) {
        externalSystemA.makePayment(amount);
    }

    @Override
    public void refundPayment(double amount) {
        externalSystemA.makeRefund(amount);
    }
}

class PaymentAdapterB implements IPaymentProcessor {
    private final ExternalPaymentSystemB externalSystemB;

    public PaymentAdapterB(ExternalPaymentSystemB externalSystemB) {
        this.externalSystemB = externalSystemB;
    }

    @Override
    public void processPayment(double amount) {
        externalSystemB.sendPayment(amount);
    }

    @Override
    public void refundPayment(double amount) {
        externalSystemB.processRefund(amount);
    }
}

class PaymentProcessorSelector {
    public static IPaymentProcessor getPaymentProcessor(String currency) {
        switch (currency) {
            case "USD":
                return new InternalPaymentProcessor();
            case "EUR":
                return new PaymentAdapterA(new ExternalPaymentSystemA());
            case "GBP":
                return new PaymentAdapterB(new ExternalPaymentSystemB());
            default:
                throw new IllegalArgumentException("Unsupported currency: " + currency);
        }
    }
}



public class Adapter {
    public static void main(String[] args) {
        String currency = "EUR";

        IPaymentProcessor paymentProcessor = PaymentProcessorSelector.getPaymentProcessor(currency);

        paymentProcessor.processPayment(100.0);
        paymentProcessor.refundPayment(50.0);

    }
}
