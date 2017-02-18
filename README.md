# Java-Command-Line
Cross-platform Command Line analogy.
HowTo:
- Клонируете
- Собираете Maven'ом

<p>Консольное приложение в твоей консоли:D
<p>Программа соответствует требованиям последнего задания здесь - https://habrahabr.ru/post/44031/
<p>Может выполнять несколько команд подряд, например dir && tree || cd .. && gedit textfile.txt. Если в конце строки ввести &, то все команды выполнятся в фоновом режиме. Весь вывод команд выполняющихся в фоновом режиме будет записываться в лог-файл, находящийся в logs/Имя_Команды/Task_ID_fulldate.txt
<p>Программа выполняет следующие команды:
<p>cd - сменить директорию
<p>dir - показать содержимое директории
<p>tree - дерево каталогов начиная с текущей директории (В задании команда pwd - показать полный путь до директории, но это слишком просто)
<p>jobs - выводит список задач выполняющихся в фоновом режиме
<p>remotemode - входит в режим удаленного управления, и ожидает соединения клиентов, которые могут управлять программой на хост-компьютере удаленно. Вывод команд отправляется всем подключенным клиентам.
<p>Если пользователь введет какую-то другую команду, не из вышеперечисленных, то программа попытается найти её среди системных, и запустить.
<p>Все перечисленные выше команды можно конфигурировать в файле settings.xml. Сменить имя/назначить команде класс. Можно установить папку для хранения логов.
<p>Проверено на операционных системах семейства Windows и Linux.
