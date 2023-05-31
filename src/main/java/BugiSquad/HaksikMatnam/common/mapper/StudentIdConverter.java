package BugiSquad.HaksikMatnam.common.mapper;

public class StudentIdConverter {

    public static Integer parseYearOfEnter(Integer studentId) {
        String studentIdStr = String.valueOf(studentId);
        String prefix = studentIdStr.substring(0, 2);
        return Integer.valueOf(prefix);
    }
}
