package example;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EmployeeDao {
    private Map<Long, Map<String, String>> employeeMap;

    public EmployeeDao() {
        employeeMap = new ConcurrentHashMap<Long, Map<String, String>>();
    }

    public Employee find(Long id) {
        Map<String, String> m = employeeMap.get(id);
        if (null == m)
            return null;
        Employee emp = new Employee();
        emp.setId(Long.parseLong(m.get("id")));
        emp.setName(m.get("name"));
        Department dep = new Department();
        dep.setId(Long.parseLong(m.get("department.id")));
        dep.setName(m.get("department.name"));
        emp.setDepartment(dep);
        return emp;
    }

    public void save(Employee emp) {
        Map<String, String> m = new HashMap<String, String>();
        m.put("id", String.valueOf(emp.getId()));
        m.put("name", emp.getName());
        m.put("department.id", String.valueOf(emp.getDepartment().getId()));
        m.put("department.name", emp.getDepartment().getName());
        employeeMap.put(emp.getId(), m);
    }

    public void remove(Long id) {
        employeeMap.remove(id);
    }

    @Override
    public String toString() {
        return employeeMap.toString();
    }
}
