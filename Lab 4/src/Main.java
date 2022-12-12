package main.kzp.lab4;

import java.io.Serializable;

public class Company implements Serializable {
    private String name;

    public Company() {
    }

    public Company(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Company: " + name + '\n';
    }
}

        package main.kzp.lab4;

        import main.kzp.lab3.Human;

        import java.io.Externalizable;
        import java.io.IOException;
        import java.io.ObjectInput;
        import java.io.ObjectOutput;

public abstract class House implements Externalizable {
    private String address;
    private Human owner;
    private double squareMeters;

    public String getAddress() {
        return address;
    }

    public Human getOwner() {
        return owner;
    }

    public double getSquareMeters() {
        return squareMeters;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setOwner(Human owner) {
        this.owner = owner;
    }

    public void setSquareMeters(double squareMeters) {
        this.squareMeters = squareMeters;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(address);
        out.writeDouble(squareMeters);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNot-FoundException {
        address = (String)in.readObject();
        squareMeters = in.readDouble();
    }

    @Override
    public String toString() {
        return "\nHouse:\n" +
                "\taddress: " + address + '\n' +
                "\towner: " + owner + '\n' +
                "\tsquareMeters: " + squareMeters + "\n";
    }
}

        package main.kzp.lab4;

        import java.io.*;
        import java.util.ArrayList;
        import java.util.List;

public class OfficeCenter extends House implements Externalizable {
    private List<OfficeRoom> officeRooms = new ArrayList<>();

    public void createNewRoom(double pricePerMonth, Company companyRenter){
        officeRooms.add(new OfficeRoom(pricePerMonth, companyRenter));
    }

    public void printAll(){
        System.out.println("#".repeat(30));
        printOfficeCenterData();
        printAllOfficeRooms();
        System.out.println("#".repeat(30));
    }

    public void printOfficeCenterData(){
        System.out.println(super.toString());
    }

    public void printAllOfficeRooms(){
        for(int i = 0; i < officeRooms.size(); ++i)
            System.out.println(String.format("Room #%d\n%s\n", i+1, of-ficeRooms.get(i)));
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
        out.writeObject(officeRooms);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNot-FoundException {
        super.readExternal(in);
        officeRooms = (List<OfficeRoom>)in.readObject();
    }

    private class OfficeRoom implements Serializable {
        private double pricePerMonth;
        private Company companyRenter;

        public OfficeRoom() {
        }

        public OfficeRoom(double pricePerMonth) {
            this.pricePerMonth = pricePerMonth;
        }

        public OfficeRoom(double pricePerMonth, Company companyRenter) {
            this.pricePerMonth = pricePerMonth;
            this.companyRenter = companyRenter;
        }

        public double getPricePerMonth() {
            return pricePerMonth;
        }

        public Company getCompanyRenter() {
            return companyRenter;
        }

        public void setPricePerMonth(double pricePerMonth) {
            this.pricePerMonth = pricePerMonth;
        }

        public void setCompanyRenter(Company companyRenter) {
            this.companyRenter = companyRenter;
        }

        @Override
        public String toString() {
            return companyRenter.toString() + "Price per month: " + pricePer-Month;
        }
    }
}

        package main.kzp.lab4;

        import main.kzp.lab3.Human;

        import java.io.*;

public class Main {
    public static void main(String[] args) {
        Human human = new Human();
        human.setFirstName("Geogiy");
        human.setLastName("Sidorov");

        Company company1 = new Company("Apple");
        Company company2 = new Company("BMW");
        Company company3 = new Company("Pepsico");

        OfficeCenter officeCenter = new OfficeCenter();
        officeCenter.setOwner(human);

        officeCenter.createNewRoom(21000, company1);
        officeCenter.createNewRoom(28800, company3);
        officeCenter.createNewRoom(12990, company2);
        officeCenter.createNewRoom(29000, company1);
        officeCenter.createNewRoom(20100, company2);
        officeCenter.createNewRoom(20100, company2);
        officeCenter.createNewRoom(21000, company3);

        officeCenter.printAll();
        serialize(officeCenter);
        officeCenter = deserialize();
        officeCenter.printAll();
    }

    private static void serialize(OfficeCenter officeCenter){
        try(FileOutputStream fos = new FileOutputStream("objectSer.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(officeCenter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static OfficeCenter deserialize(){
        try(FileInputStream fis = new FileInputStream("objectSer.ser");
            ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (OfficeCenter)ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
