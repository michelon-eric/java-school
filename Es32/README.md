# ***Simulazione Artistra di Strada e Clienti***

## [Semafori](src/michelon/Main.java)
Sono stati importati i semafori dalla libreria java.util.concurrent.Semaphore
<!-- ![codice: import java.util.concurrent.Semaphore](md-images/main-semaphore-import.png) -->
```java
    import java.util.concurrent.Semaphore
```

Sono stati utilizzati due semafori
1. **chairsSemaphore**: per gestire le sedie libere ed occupate
2. **artistSemaphore**: per gestire il cliente che sta occupando l'artista per avere un ritratto 
<!-- ![Immagine della dichiarazione di due semaphori nella classe Main](md-images/main-semaphore-declaration.png) -->
```java
    private static Semaphore chairsSemaphore;
    private static Semaphore artistSemaphore;
    ...
    chairsSemaphore = new Semaphore(Data.NUM_CHAIRS);
    artistSemaphore = new Semaphore(1);
```  
<!-- ![inizialisation of the semaphores](md-images/main-semaphore-initialization.png) -->

<br/><br/>

---
<br/><br/>

## [Cliente](src/michelon/Customer.java)
Il metodo Customer.run può essere suddiviso in due parti
1. *Ottenimento di una sedia*
<!-- ![Immagine con contenuto del metodo Customer.run](md-images/customer-run-1.png) -->
```java
    if (!chairsSemaphore.tryAcquire(
            Data.MIN_WAIT_TIME + new Random().nextInt(Data.MAX_WAIT_TIME),
            TimeUnit.MILLISECONDS)) {
        print("Client is leaving");
        return;
    }
```
- **[*if (!chairsSemaphore.tryAcquire(...), ...)*]:** il cliente cerca di ottenere una sedia per un determinato tempo se fallisce, si allontana
2. *Ottenimento del ritratto*
<!-- ![Immagine con contenuto del metodo Customer.run](md-images/customer-run-2.png) -->
```java
    while (true) {
        if (!artistSemaphore.tryAcquire()) continue;
        
        Thread.sleep(Data.MIN_DRAWING_TIME + new Random().nextInt(Data.MAX_DRAWING_TIME));
        print("Customer got the painting");
        chairsSemaphore.release();
        artistSemaphore.release();
        break;
    }
```
- **[*if (!artistSemaphore.tryAcquire()) continue*]:** se l'artista non è disponibile, aspetta 
- **[*Thread.sleep(...)*]:** ottenimento del ritratto simulata con un'attesa di tempo randomico
- **[*chairsSemaphore.release()*]:** il cliente si alza e libera una sedia 
- **[*artistSemaphore.release()*]:** l'artista ha completato il ritratto e può farne un'altro
