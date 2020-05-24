# TestApp
Инструкция по запуску приложения:
1) Скачайте проект.
2) Откройте папку с только что скаченным проектом.
3) В файле src/main/resources/META-INF/persistence.xml выставите Ваши настройки в строках 11, 12 и 13.
4) Перейдите в файл src/main/resources/db.migration и запустите скрипт V1__Create_Schema.sql, создающий и заполняющий базу данных.
5) Откройте раздел File -> Project Structure -> Artifacts -> Add -> JAR -> From modules with dependencies и выберите в качестве Main Class класс Application.App
6) Затем в разделе Output directory оставьте путь только для папки с проектом (Пример: C:\user\TestApp\)
7) После этого нажмите Build -> Build Artifacts. В папке с проектом появиться TestApp.jar
8) Теперь откройте командную строку, находясь в папке с проектом.
9) Пропишите одну из предложенных команд
   java -jar TestApp.jar search 1.json out1.json (создаться файл out1.json, куда будет помещен результат поиска 1.json)
   java -jar TestApp.jar search 1.json (результат поиска будет помещен в файл по умолчанию, т.е. out.json)
Вместо search можно использовать параметр stat.

Примеры входный файлов Вы можете найти в директории src/main/resources
Примеры выходных файлов Вы можете найти в директории src/outFiles 