package example;

public class Main {
    public static void main(String[] args) {
        Department hrDep = new Department();
        hrDep.setId(1L);
        hrDep.setName("HR");
        Department rdDep = new Department();
        rdDep.setId(2L);
        rdDep.setName("R&D");

        EmployeeDao dao = new EmployeeDao();

        // create alice
        Employee alice = new Employee();
        alice.setId(1L);
        alice.setName("alice");
        alice.setDepartment(hrDep);
        dao.save(alice);

        // create bob 
        Employee bob = new Employee();
        bob.setId(2L);
        bob.setName("bob");
        bob.setDepartment(rdDep);
        dao.save(bob);

        // update alice
        alice = dao.find(1L);
        alice.setDepartment(rdDep);
        dao.save(alice);

        // remove bob
        dao.remove(bob.getId());

        // remove alice 
        dao.remove(alice.getId());
    }
}
