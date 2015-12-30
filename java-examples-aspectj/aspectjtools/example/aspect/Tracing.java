package example.tracing;

import example.Employee;
import example.EmployeeDao;

public aspect Tracing {
    private static final String INDENT = "  ";

    pointcut fetch(Long id):
        call(* *Dao.find(Long)) && args(id);

    pointcut store(Object o):
        call(void *Dao.save(..))
            && args(o);

    pointcut modify():
        call(* *Dao.save(..)) ||
        call(* *Dao.remove(..));

    before():
        call(* *Dao.*(..))  {
        System.out.println(thisJoinPoint.getTarget().getClass().getSimpleName()
                + "#" + thisJoinPoint.getSignature().getName());
    }

    Object around(Long id): fetch(id) {
        Object o = proceed(id);
        System.out.println(INDENT
                + thisJoinPoint.getTarget().getClass().getSimpleName()
                + " fetched " + o);
        return o;
    }

    before(Object o): store(o) {
        System.out.println(INDENT
                + thisJoinPoint.getTarget().getClass().getSimpleName()
                + " is about to store " + o);
    }

    after() returning: modify() {
        Object target = thisJoinPoint.getTarget();
        System.out.println(INDENT
                + target.getClass().getSimpleName()
                + ": " + target);
    }
}
