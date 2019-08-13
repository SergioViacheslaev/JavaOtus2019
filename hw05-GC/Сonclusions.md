# Анализ работы сборщиков мусора в JVM OpenJDK 12.0.1

## Тестовый стенд - консольная программа (описание)
   Программа выполняется примерно 5 минут, за это время происходит
   медленное подтекание по памяти, что приводит к исключению
   java.lang.OutOfMemoryError: Java heap space.
   
   В качестве дополнительного критерия оценки производительности работы
   приложения создан класс PasswordFactory, который генерирует случайные
   пароли. Запуская приложение с разными GC посмотрим, в каком случае
   будет сгенерировано больше паролей и за какое время.
   
   В методе MemoryTasks.consumeMemory() происходит постоянное выделение
   памяти под объекты типа String в списке LinkedList, затем часть
   объектов удаляется, а часть остается, тем самым переходя в будущем в
   Old gen. Метод MemoryTasks.setConsumeFactor(0.2d) указывает на
   удаление 20% всех элементов списка.
   
   Во время работы программы метод showFreeMemory() класса SystemInfo
   постоянно показывает сколько осталось свободной памяти, тем самым
   можно отслеживать как скоро завершится программа.
   
   Класс GCMonitor позволяет подписаться на события сборщика мусора,
   информация о сборках также выводится в консоль в реальном времени.
   
  **Исходные данные:** процессор IntelCore i7 (3.4ггц), 16GB RAM
  -Xms512m -Xmx512m память под JVM heap.
  
  Для анализа логов использован сервис https://gceasy.io, для просмотра
  работы приложения и дампа памяти visualvm.
  
**1. -XX:+UseSerialGC (Serial Collector)**
 *Результат работы программы:*
 
 Random passwords generated: 5384
 Time spent: 336 seconds
 [Лог Serial Collector на gceasy](https://gceasy.io/my-gc-report.jsp?p=c2hhcmVkLzIwMTkvMDgvMTIvLS1nYy03ODIwLTIwMTktMDgtMTJfMTktMTUtMTIubG9nLS0xMS01OS0xOA==&channel=WEB)
 
 
**2. -XX:+UseParallelGC (Parallel Collector)** *Результат работы
программы:* Random passwords generated: 4334 Time spent: 278 seconds
[Лог Parallel Collector на gceasy](https://gceasy.io/my-gc-report.jsp?p=c2hhcmVkLzIwMTkvMDgvMTIvLS1nYy0xOTI4NC0yMDE5LTA4LTEyXzE5LTU5LTMwLmxvZy0tMTctNy05&channel=WEB)


**3.-XX:+UseConcMarkSweepGC (CMS)** *Результат работы программы:* Random
passwords generated: 5384 Time spent: 338 seconds [Лог CMS  на gceasy](https://gceasy.io/my-gc-report.jsp?p=c2hhcmVkLzIwMTkvMDgvMTEvLS1nYy02NTY4LTIwMTktMDgtMTJfMjAtMTEtMTYubG9nLS0yMS00LTQ4&channel=WEB)


**4. -XX:+UseG1GC (G1)** *Результат работы программы:* Random passwords
generated: 5561 Time spent: 325 seconds
[Лог G1  на gceasy](https://gceasy.io/my-gc-report.jsp?p=c2hhcmVkLzIwMTkvMDgvMTIvLS1nYy02NTIwLTIwMTktMDgtMTJfMjAtMjItMDcubG9nLS0xNy0yOC01OA==&channel=WEB)

 

График производительности по количеству сгенерированных паролей до OutOfMemoryError.

График по общему времени простоя приложения в фазе Stop the World


**Вывод:** В моем случае - наибольший Throughput показало приложение с
G1 сборщиком мусора - 95.7%. Также и самый высокий показатель по
количеству сгенерированных паролей 5561. Это связано с тем, что время
простоя приложения в фазе STW у G1 самое низкое по сравнению с другими
сборщиками мусора - всего 13.9 секунд по сравнению с Parallel сборщиком,
у которого общее время сборки мусора в фазе STW почти 32 секунды. Как
результат с G1 получилось совершить больше полезной работы.



   
   
   
   
   
