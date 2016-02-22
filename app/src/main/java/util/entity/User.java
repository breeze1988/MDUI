package util.entity;

/**
 * Created by Administrator on 2016/2/22.
 */
public class User {
    private String firstName;  //必传参数
    private String lastName;   //必传参数
    private int age;           //可选参数
    private String phone;
    private String address;

    public User(UserBuilder builder){
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.age = builder.age;
        this.phone = builder.phone;
        this.address = builder.address;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public static class UserBuilder{
        private final String firstName;
        private final String lastName;
        private int age;
        private String phone;
        private String address;

        public UserBuilder(String firstName,String lastName){
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public UserBuilder age(int age){
            this.age = age;
            return this;
        }

        public UserBuilder phone(String phone){
            this.phone = phone;
            return this;
        }

        public UserBuilder address(String address){
            this.address = address;
            return this;
        }

        public User build(){
            User user = new User(this);
            if(user.getAge() > 120){
                throw new IllegalStateException("age out of range"); //线程安全
            }
            return user;
        }
    }
    public static void main(String[] args){
        User user = new User.UserBuilder("wang","breeze").age(28).build();
        System.out.println(user.toString());
    }

}
