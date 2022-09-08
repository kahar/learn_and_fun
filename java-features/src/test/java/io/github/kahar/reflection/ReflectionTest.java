package io.github.kahar.reflection;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReflectionTest {
    private static List<String> getFieldNames(Field[] fields) {
        List<String> fieldNames = new ArrayList<>();
        for (Field field : fields)
            fieldNames.add(field.getName());
        return fieldNames;
    }

    private static List<String> getMethodNames(Method[] methods) {
        List<String> methodNames = new ArrayList<>();
        for (Method method : methods)
            methodNames.add(method.getName());
        return methodNames;
    }

    @Test
    public void givenObject_whenGetsFieldNamesAtRuntime_thenCorrect() {
        Object person = new Person();
        Field[] fields = person.getClass().getDeclaredFields();

        List<String> actualFieldNames = getFieldNames(fields);

        assertTrue(Arrays.asList("name", "age")
                .containsAll(actualFieldNames));
    }

    @Test
    public void givenObject_whenGetsClassName_thenCorrect() {
        Object goat = new Goat("goat");
        Class<?> clazz = goat.getClass();

        assertEquals("Goat", clazz.getSimpleName());
        assertEquals("io.github.kahar.reflection.Goat", clazz.getName());
        assertEquals("io.github.kahar.reflection.Goat", clazz.getCanonicalName());
    }

    @Test
    public void givenClassName_whenCreatesObject_thenCorrect() throws ClassNotFoundException {
        Class<?> clazz = Class.forName("io.github.kahar.reflection.Goat");

        assertEquals("Goat", clazz.getSimpleName());
        assertEquals("io.github.kahar.reflection.Goat", clazz.getName());
        assertEquals("io.github.kahar.reflection.Goat", clazz.getCanonicalName());
    }

    @Test
    public void givenClass_whenRecognisesModifiers_thenCorrect() throws ClassNotFoundException {
        Class<?> goatClass = Class.forName("io.github.kahar.reflection.Goat");
        Class<?> animalClass = Class.forName("io.github.kahar.reflection.Animal");

        int goatMods = goatClass.getModifiers();
        int animalMods = animalClass.getModifiers();

        assertTrue(Modifier.isPublic(goatMods));
        assertTrue(Modifier.isAbstract(animalMods));
        assertTrue(Modifier.isPublic(animalMods));
    }

    @Test
    public void givenClass_whenGetsPackageInfo_thenCorrect() {
        Goat goat = new Goat("goat");
        Class<?> goatClass = goat.getClass();
        Package pkg = goatClass.getPackage();

        assertEquals("io.github.kahar.reflection", pkg.getName());
    }

    @Test
    public void givenClass_whenGetsSuperClass_thenCorrect() {
        Goat goat = new Goat("goat");
        String str = "any string";

        Class<?> goatClass = goat.getClass();
        Class<?> goatSuperClass = goatClass.getSuperclass();

        assertEquals("Animal", goatSuperClass.getSimpleName());
        assertEquals("Object", str.getClass().getSuperclass().getSimpleName());
    }

    @Test
    public void givenClass_whenGetsImplementedInterfaces_thenCorrect() throws ClassNotFoundException {
        Class<?> goatClass = Class.forName("io.github.kahar.reflection.Goat");
        Class<?> animalClass = Class.forName("io.github.kahar.reflection.Animal");

        Class<?>[] goatInterfaces = goatClass.getInterfaces();
        Class<?>[] animalInterfaces = animalClass.getInterfaces();

        assertEquals(1, goatInterfaces.length);
        assertEquals(1, animalInterfaces.length);
        assertEquals("Locomotion", goatInterfaces[0].getSimpleName());
        assertEquals("Eating", animalInterfaces[0].getSimpleName());
    }

    @Test
    public void givenClass_whenGetsConstructor_thenCorrect() throws ClassNotFoundException {
        Class<?> goatClass = Class.forName("io.github.kahar.reflection.Goat");

        Constructor<?>[] constructors = goatClass.getConstructors();

        assertEquals(1, constructors.length);
        assertEquals("io.github.kahar.reflection.Goat", constructors[0].getName());
    }

    @Test
    public void givenClass_whenGetsFields_thenCorrect() throws ClassNotFoundException {
        Class<?> animalClass = Class.forName("io.github.kahar.reflection.Animal");
        Field[] fields = animalClass.getDeclaredFields();

        List<String> actualFields = getFieldNames(fields);

        assertEquals(2, actualFields.size());
        assertTrue(actualFields.containsAll(Arrays.asList("name", "CATEGORY")));
    }

    @Test
    public void givenClass_whenGetsMethods_thenCorrect() throws ClassNotFoundException {
        Class<?> animalClass = Class.forName("io.github.kahar.reflection.Animal");
        Method[] methods = animalClass.getDeclaredMethods();
        List<String> actualMethods = getMethodNames(methods);

        assertEquals(3, actualMethods.size());
        assertTrue(actualMethods.containsAll(Arrays.asList("getName",
                "setName", "getSound")));
    }

    @Test
    public void givenClass_whenGetsAllConstructors_thenCorrect() throws ClassNotFoundException {
        Class<?> birdClass = Class.forName("io.github.kahar.reflection.Bird");
        Constructor<?>[] constructors = birdClass.getConstructors();

        assertEquals(3, constructors.length);
    }

    @Test
    public void givenClass_whenGetsEachConstructorByParamTypes_thenCorrect() throws ClassNotFoundException, NoSuchMethodException {
        Class<?> birdClass = Class.forName("io.github.kahar.reflection.Bird");

        Constructor<?> cons1 = birdClass.getConstructor();
        Constructor<?> cons2 = birdClass.getConstructor(String.class);
        Constructor<?> cons3 = birdClass.getConstructor(String.class, boolean.class);
    }

    @Test
    public void givenClass_whenInstantiatesObjectsAtRuntime_thenCorrect() throws NoSuchMethodException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> birdClass = Class.forName("io.github.kahar.reflection.Bird");
        Constructor<?> cons1 = birdClass.getConstructor();
        Constructor<?> cons2 = birdClass.getConstructor(String.class);
        Constructor<?> cons3 = birdClass.getConstructor(String.class,
                boolean.class);

        Bird bird1 = (Bird) cons1.newInstance();
        Bird bird2 = (Bird) cons2.newInstance("Weaver bird");
        Bird bird3 = (Bird) cons3.newInstance("dove", true);

        assertEquals("bird", bird1.getName());
        assertEquals("Weaver bird", bird2.getName());
        assertEquals("dove", bird3.getName());

        assertFalse(bird1.walks());
        assertTrue(bird3.walks());
    }

    @Test
    public void givenClass_whenGetsPublicFields_thenCorrect() throws ClassNotFoundException {
        Class<?> birdClass = Class.forName("io.github.kahar.reflection.Bird");
        Field[] fields = birdClass.getFields();

        assertEquals(1, fields.length);
        assertEquals("CATEGORY", fields[0].getName());
    }

    @Test
    public void givenClass_whenGetsPublicFieldByName_thenCorrect() throws NoSuchFieldException, ClassNotFoundException {
        Class<?> birdClass = Class.forName("io.github.kahar.reflection.Bird");
        Field field = birdClass.getField("CATEGORY");

        assertEquals("CATEGORY", field.getName());
    }

    @Test
    public void givenClass_whenGetsDeclaredFields_thenCorrect() throws ClassNotFoundException {
        Class<?> birdClass = Class.forName("io.github.kahar.reflection.Bird");
        Field[] fields = birdClass.getDeclaredFields();

        assertEquals(1, fields.length);
        assertEquals("walks", fields[0].getName());
    }

    @Test
    public void givenClass_whenGetsFieldsByName_thenCorrect() throws ClassNotFoundException, NoSuchFieldException {
        Class<?> birdClass = Class.forName("io.github.kahar.reflection.Bird");
        Field field = birdClass.getDeclaredField("walks");

        assertEquals("walks", field.getName());
    }

    @Test
    public void givenClassField_whenGetsType_thenCorrect() throws ClassNotFoundException, NoSuchFieldException {
        Field field = Class.forName("io.github.kahar.reflection.Bird")
                .getDeclaredField("walks");
        Class<?> fieldClass = field.getType();

        assertEquals("boolean", fieldClass.getSimpleName());
    }

    @Test
    public void givenClassField_whenSetsAndGetsValue_thenCorrect() throws ClassNotFoundException, NoSuchMethodException, NoSuchFieldException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> birdClass = Class.forName("io.github.kahar.reflection.Bird");
        Bird bird = (Bird) birdClass.getConstructor().newInstance();
        Field field = birdClass.getDeclaredField("walks");
        field.setAccessible(true);

        assertFalse(field.getBoolean(bird));
        assertFalse(bird.walks());

        field.set(bird, true);

        assertTrue(field.getBoolean(bird));
        assertTrue(bird.walks());
    }

    @Test
    public void givenClassField_whenGetsAndSetsWithNull_thenCorrect() throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException {
        Class<?> birdClass = Class.forName("io.github.kahar.reflection.Bird");
        Field field = birdClass.getField("CATEGORY");
        field.setAccessible(true);

        assertEquals("domestic", field.get(null));
    }

    @Test
    @SneakyThrows
    public void givenClass_whenGetsAllPublicMethods_thenCorrect() {
        Class<?> birdClass = Class.forName("io.github.kahar.reflection.Bird");
        Method[] methods = birdClass.getMethods();
        List<String> methodNames = getMethodNames(methods);

        assertTrue(methodNames.containsAll(Arrays
                .asList("equals", "notifyAll", "hashCode",
                        "walks", "eats", "toString")));
    }

    @Test
    @SneakyThrows
    public void givenClass_whenGetsOnlyDeclaredMethods_thenCorrect() {
        Class<?> birdClass = Class.forName("io.github.kahar.reflection.Bird");
        List<String> actualMethodNames
                = getMethodNames(birdClass.getDeclaredMethods());

        List<String> expectedMethodNames = Arrays
                .asList("isWalks", "setWalks", "walks", "getSound", "eats");

        assertEquals(expectedMethodNames.size(), actualMethodNames.size());
        assertTrue(expectedMethodNames.containsAll(actualMethodNames));
        assertTrue(actualMethodNames.containsAll(expectedMethodNames));
    }

    @Test
    @SneakyThrows
    public void givenMethodName_whenGetsMethod_thenCorrect() throws Exception {
        Bird bird = new Bird();
        Method walksMethod = bird.getClass().getDeclaredMethod("walks");
        Method setWalksMethod = bird.getClass().getDeclaredMethod("setWalks", boolean.class);

        assertTrue(walksMethod.canAccess(bird));
        assertTrue(setWalksMethod.canAccess(bird));
    }

    @Test
    @SneakyThrows
    public void givenMethod_whenInvokes_thenCorrect() {
        Class<?> birdClass = Class.forName("io.github.kahar.reflection.Bird");
        Bird bird = (Bird) birdClass.getConstructor().newInstance();
        Method setWalksMethod = birdClass.getDeclaredMethod("setWalks", boolean.class);
        Method walksMethod = birdClass.getDeclaredMethod("walks");
        boolean walks = (boolean) walksMethod.invoke(bird);

        assertFalse(walks);
        assertFalse(bird.walks());

        setWalksMethod.invoke(bird, true);

        boolean walks2 = (boolean) walksMethod.invoke(bird);
        assertTrue(walks2);
        assertTrue(bird.walks());
    }
}
