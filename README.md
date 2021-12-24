<div id="_pbportletlab5_WAR_pbportlet_pb-lab5-text"><h3>Техническое Задание</h3> <p>Реализовать консольное приложение, которое реализует управление коллекцией объектов в интерактивном режиме. В коллекции необходимо хранить объекты класса <code>StudyGroup</code>, описание которого приведено ниже.</p> <p><b>Разработанная программа должна удовлетворять следующим требованиям:</b></p> <ul><li>Класс, коллекцией экземпляров которого управляет программа, должен реализовывать сортировку по умолчанию.</li><li>Все требования к полям класса (указанные в виде комментариев) должны быть выполнены.</li><li>Для хранения необходимо использовать коллекцию типа <code>java.util.Vector</code></li><li>При запуске приложения коллекция должна автоматически заполняться значениями из файла.</li><li>Имя файла должно передаваться программе с помощью: <b>аргумент командной строки</b>.</li><li>Данные должны храниться в файле в формате <code>json</code></li><li>Чтение данных из файла необходимо реализовать с помощью класса <code>java.util.Scanner</code></li><li>Запись данных в файл необходимо реализовать с помощью класса <code>java.io.BufferedOutputStream</code></li><li>Все классы в программе должны быть задокументированы в формате javadoc.</li><li>Программа должна корректно работать с неправильными данными (ошибки пользовательского ввода, отсутсвие прав доступа к файлу и т.п.).</li></ul> <p><b>В интерактивном режиме программа должна поддерживать выполнение следующих команд:</b></p> <ul><li><code>help</code> : вывести справку по доступным командам</li><li><code>info</code> : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)</li><li><code>show</code> : вывести в стандартный поток вывода все элементы коллекции в строковом представлении</li><li><code>add {element}</code> : добавить новый элемент в коллекцию</li><li><code>update id {element}</code> : обновить значение элемента коллекции, id которого равен заданному</li><li><code>remove_by_id id</code> : удалить элемент из коллекции по его id</li><li><code>clear</code> : очистить коллекцию</li><li><code>save</code> : сохранить коллекцию в файл</li><li><code>execute_script file_name</code> : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.</li><li><code>exit</code> : завершить программу (без сохранения в файл)</li><li><code>remove_at index</code> : удалить элемент, находящийся в заданной позиции коллекции (index)</li><li><code>remove_last</code> : удалить последний элемент из коллекции</li><li><code>remove_lower {element}</code> : удалить из коллекции все элементы, меньшие, чем заданный</li><li><code>count_by_students_count studentsCount</code> : вывести количество элементов, значение поля studentsCount которых равно заданному</li><li><code>count_less_than_semester_enum semesterEnum</code> : вывести количество элементов, значение поля semesterEnum которых меньше заданного</li><li><code>print_field_descending_group_admin</code> : вывести значения поля groupAdmin всех элементов в порядке убывания</li></ul> <p><b>Формат ввода команд:</b></p> <ul><li>Все аргументы команды, являющиеся стандартными типами данных (примитивные типы, классы-оболочки, String, классы для хранения дат), должны вводиться в той же строке, что и имя команды.</li><li>Все составные типы данных (объекты классов, хранящиеся в коллекции) должны вводиться по одному полю в строку.</li><li>При вводе составных типов данных пользователю должно показываться приглашение к вводу, содержащее имя поля (например, "Введите дату рождения:")</li><li>Если поле является enum'ом, то вводится имя одной из его констант (при этом список констант должен быть предварительно выведен).</li><li>При некорректном пользовательском вводе (введена строка, не являющаяся именем константы в enum'е; введена строка вместо числа; введённое число не входит в указанные границы и т.п.) должно быть показано сообщение об ошибке и предложено повторить ввод поля.</li><li>Для ввода значений null использовать пустую строку.</li><li>Поля с комментарием "Значение этого поля должно генерироваться автоматически" не должны вводиться пользователем вручную при добавлении.</li></ul> <p><b>Описание хранимых в коллекции классов: </b></p> <pre><code>public class StudyGroup {
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int studentsCount; //Значение поля должно быть больше 0
    private FormOfEducation formOfEducation; //Поле не может быть null
    private Semester semesterEnum; //Поле может быть null
    private Person groupAdmin; //Поле не может быть null
}
public class Coordinates {
    private double x;
    private float y; //Максимальное значение поля: 110
}
public class Person {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private java.util.Date birthday; //Поле не может быть null
    private Long height; //Поле может быть null, Значение поля должно быть больше 0
    private Double weight; //Поле не может быть null, Значение поля должно быть больше 0
    private String passportID; //Поле не может быть null
}
public enum FormOfEducation {
    DISTANCE_EDUCATION,
    FULL_TIME_EDUCATION,
    EVENING_CLASSES;
}
public enum Semester {
    SECOND,
    FOURTH,
    FIFTH,
    EIGHTH;
}
</code></pre></div>