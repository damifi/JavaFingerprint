Autoren sind: Aleksej Davletcurin, Daniel Fischer, Rustam Magomedov

Aleksej Davletcurin hat sich haupts�chlich mit "LCSAlgorithm" und "LCSTask" besch�ftigt.

Daniel Fischer hat sich haupts�chlich mit "Configuration", "Fingerprint", "Printloader" und "Resultwriter"  besch�ftigt.

Rustam Magomedov hat sich haupts�chlich mit dem "Threadpool" und "LCSApp" besch�ftigt.


/****Klassenbeschreibung****/


Configuration: l�dt die Konfigurationsdatei wie in der Aufgabe beschrieben hoch.

Fingerprint: Innere Darstellung des Strings (id: Wert Wert Wert).

LCSAlgorithm: Da ist der Algorithmus(compare Funktion) implementiert, die Funktion count ID's, die ID's z�hlt und die Funktion, die ID's sortiert.

LCSApp: Main Funktion, die Configuration, Printloader, Threadpool, ResultWriter nacheinander ausf�hrt.

LCSTask: Da ist Taskaufgabe implementiert und Ergebnisausgabe beschrieben. Die Threads schreiben synchronisiert in die Ergebnisliste. 

Printloader: Konventiert String zur inneren Darstellung.

ResultWriter: Schreibt die Ergebnisse synchronisiert in eine Datein rein.

ThreadPool: Threads und Warteschlange(Qeue) implementiert. Warteschlange ist synchronisiert. Worker sind Threads und bekommen einen Task �bergeben. 
Solange die Warteschlange leer ist-warten die Threads. 

/******Info******/
Der Speedup-Test funktioniert nicht immer. Es kann sein, dass er 3 Mal in Folge funktioniert und anschlie�end nicht. Es kann sein dass er beim zweiten Mal funktioniert und beim ersten nicht.
