Autoren sind: Aleksej Davletcurin, Daniel Fischer, Rustam Magomedov

Aleksej Davletcurin hat sich hauptsächlich mit "LCSAlgorithm" und "LCSTask" beschäftigt.

Daniel Fischer hat sich hauptsächlich mit "Configuration", "Fingerprint", "Printloader" und "Resultwriter"  beschäftigt.

Rustam Magomedov hat sich hauptsächlich mit dem "Threadpool" und "LCSApp" beschäftigt.


/****Klassenbeschreibung****/


Configuration: lädt die Konfigurationsdatei wie in der Aufgabe beschrieben hoch.

Fingerprint: Innere Darstellung des Strings (id: Wert Wert Wert).

LCSAlgorithm: Da ist der Algorithmus(compare Funktion) implementiert, die Funktion count ID's, die ID's zählt und die Funktion, die ID's sortiert.

LCSApp: Main Funktion, die Configuration, Printloader, Threadpool, ResultWriter nacheinander ausführt.

LCSTask: Da ist Taskaufgabe implementiert und Ergebnisausgabe beschrieben. Die Threads schreiben synchronisiert in die Ergebnisliste. 

Printloader: Konventiert String zur inneren Darstellung.

ResultWriter: Schreibt die Ergebnisse synchronisiert in eine Datein rein.

ThreadPool: Threads und Warteschlange(Qeue) implementiert. Warteschlange ist synchronisiert. Worker sind Threads und bekommen einen Task übergeben. 
Solange die Warteschlange leer ist-warten die Threads. 

/******Info******/
Der Speedup-Test funktioniert nicht immer. Es kann sein, dass er 3 Mal in Folge funktioniert und anschließend nicht. Es kann sein dass er beim zweiten Mal funktioniert und beim ersten nicht.
