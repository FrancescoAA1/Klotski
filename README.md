<h1>KLOTSKI</h1>

## **Il gioco**

Klotski è un puzzle costituito da una scatola di dimensioni 4x5 in cui
sono inseriti blocchi colorati di dimensioni variabili, tra cui un
blocco quadrato rosso di lato 2 caselle. L'obiettivo del gioco è quello
di posizionare il blocco rosso in prossimità dell'uscita presente sul
fondo della tabella adoperando il minor numero di mosse possibili. Per
fare ciò, l'utente ha la possibilità di spostare verticalmente oppure
orizzontalmente i blocchi, verificando che il blocco mosso non vada a
finire in caselle occupate.

Il gioco, nato nel XX secolo, è stato distribuito in numerose
configurazioni, differenti l'una dall'altra per tipologie di blocchi e
grado di difficoltà. In questa versione desktop, l'utente potrà mettere
alla prova le proprie abilità logiche con alcune delle configurazioni
più conosciute, il tutto tramite funzionalità aggiuntive che favoriscano
una risoluzione più veloce del rompicapo.

__________________________________________________________________________

All'avvio del gioco, viene visualizzato un menu principale intuitivo con
le seguenti quattro opzioni (presentate in senso orario dal pulsante in
alto a sinistra):

#### 1. **Nuova Partita**:
Questa opzione consente all\'utente di iniziare una nuova partita. L\'utente può
scegliere una delle configurazioni preesistenti e iniziare a
giocare immediatamente. Alternativamente, l'utente può scegliere di
tornare al menu principale cliccando sulla freccia in alto a sinistra.

#### 2. **Carica Partita**: 
L\'opzione \"Carica Partita\" consente all\'utente
di riprendere una partita precedentemente salvata. Per ogni
partita salvata, è indicata il numero della configurazione, il numero di
mosse già effettuate, la data di inizio e lo stato della stessa. Tale
modalità permette all\'utente di tornare a un punto specifico del gioco
senza perdere i progressi precedenti. Anche in questo caso l'utente può
tornare al menu principale cliccando sulla freccia in alto a sinistra.

#### 3. **Esci**: 
L\'opzione \"Esci\" consente all\'utente di chiudere il gioco.

#### 4. **Crediti**: 
Nella sezione \"Crediti\", l\'utente può visualizzare i
nomi degli sviluppatori del gioco Klotski. Cliccando su ciascuna icona,
è possibile visualizzare i profili LinkedIn di ciascun developer.

_________________________________________________________________________

Nel caso di selezione delle opzioni **Nuova Partita** e **Carica
Partita**, dopo la scelta della configurazione o della partita,
l\'utente viene presentato con il classico riquadro di gioco, che
rappresenta il campo di gioco del puzzle. Qui l'utente può trascinare i
blocchi con il mouse, tornare alla mossa precedente, salvare la partita
in corso, ottenere suggerimenti per la prossima mossa migliore e tornare
alla lista delle configurazioni disponibili. Si elencano in senso orario (a
partire dall'angolo in alto a sinistra) i pulsanti che consentono di
svolgere le funzionalità menzionate sopra:

-   Pulsante **Home**, per ritornare al menu

-   Pulsante **Level**, per ritornare alla
    finestra delle configurazioni 

-   Pulsante **Hint**, per ottenere suggerimenti; come indicato nelle
    specifiche del progetto, il gioco è in grado di risolvere ciascuna
    configurazione dall'inizio della partita senza l'intervento
    dell'utente. Al contrario, nel caso in cui il giocatore avvii la
    partita e provi una configurazione di mosse che non coincidono con
    la soluzione ottimale, la funzionalità Hint punta a ripristinare la
    configurazione iniziale (decrementando il numero di mosse), per poi
    eseguire la soluzione migliore.

> Nota Importante: cliccando con il tasto destro sul pulsante, è possibile
> vedere una risoluzione velocizzata della configurazione.

-   Pulsante **Save**, per salvare una qualsiasi partita. Il salvataggio
    della partita in corso è immediatamente disponibile nella sezione
    "Carica Partita", e permette all'utente di ripristinando le mosse
    precedentemente eseguite.

-   Pulsante **Undo**, per annullare la mossa corrente. Tale funzionalità
    non è ovviamente disponibile all'inizio di una nuova partita, oppure
    nel caso lo storico di una partita salvata sia stato eliminato.

-   Pulsante **Home**, per ritornare al menu principale.

 Come nella versione fisica del gioco, una partita si conclude con il
 posizionamento del blocco rosso in corrispondenza dell'apertura nella
 parte bassa e centrale del riquadro blu. In tal caso, il giocatore
 potrà visualizzare il banner di vittoria, che mostra il numero di
 mosse eseguite e gli indizi forniti. Inoltre, la finestra fornisce la
 possibilità di tornare al menu (pulsante **Home**) oppure di ritornare
 alla mossa precedente la vittoria (pulsante **Continue**).

 Istruzioni d’Uso
1. Per scaricare l’applicativo, accedi anzitutto al seguente link:
https://github.com/FrancescoAA1/Klotski/releases/ Qui troverai due versioni del gioco, disponibili
rispettivamente per sistemi operativi Windows e MacOS.
2. Individua la versione corrispondente al tuo sistema operativo e clicca sul file Klotski.jar per avviare il
download del programma.
3. Una volta completato il processo, troverai il file nella cartella Download del tuo computer.
4. A questo punto, prima di avviare l'eseguibile, assicurati di avere Java installato sulla tua macchina. Puoi
verificare se Java è già installato seguendo questi passaggi:
• Apri il Prompt dei comandi di Windows o il Terminale di MacOS.
• Digita il comando java -version e premi Invio.
• Se il comando restituisce informazioni sulla versione di Java installata, significa che Java è già
presente sulla tua macchina. In tal caso, puoi passare al passaggio successivo.
• Se il comando non viene riconosciuto o se non hai Java installato, puoi scaricare quest’ultimo dal sito
ufficiale: https://www.java.com/it/download/manual.jsp
• Assicurati inoltre di aver installato la versione di JDK (versione 17 o successive) necessaria a poter
eseguire le librerie utilizzate dal Klotski.jar. Se non hai un JDK installato puoi scaricarlo direttamente
dal sito ufficiale seguendo il link: https://www.oracle.com/java/technologies/downloads/
5. Una volta completato il download e l'installazione di Java (se necessario), torna alla cartella di download
del tuo computer e fai doppio clic sul file .jar del gioco Klotski.
L'eseguibile del gioco Klotski si aprirà e potrai iniziare a giocare.
Nota: dal link riportato nel primo passaggio, è possibile scaricare i file zip del gioco al fine di ispezionare il
codice.
Nota per utenti MacOS: al fine di poter eseguire correttamente il file Klotski.jar sono necessarie le
seguenti operazioni:
• Consentire l’esecuzione di applicazioni provenienti da sviluppatori non identificati. Per fare
questo, aprire le “Impostazioni di Sistema” recarsi alla voce “Privacy e sicurezza” e dare il
consenso all’esecuzione di KlotskiMacOS.jar cliccando sull’apposito pulsante e inserendo le
credenziali dell’utente corrente (che deve possedere di privilegi di root).
• A questo punto, per poter avviare KlotskiMacOS.jar con un doppio click sull’icona è
indispensabile spostare il file all’interno di una cartella con privilegi di root per tutti gli utenti. Per
fare ciò, creare una cartella, cliccare il tasto desto su di essa e premere sulla voce “Ottieni
Informazioni”. Dopo aver raggiunto la sezione “Condivisione e permessi” selezionare i privilegi di
“lettura e scrittura” per ogni utente che abbia accesso a quella cartella.
• Se si vuole invece eseguire KlotskiMacOS.jar in cartelle qualsiasi che non rispecchino i requisiti
del punto precedente è necessario avviare il file dal terminale. Per fare ciò apri un terminale
nella cartella che contiene KlotskiMacOS.jar ed esegui il seguente comando:
sudo java -jar KlotskiMacOS.jar
(In tal caso nella cartella che contiene il file verrà creata la cartella “target” dal gioco contenete al suo interno i file di
log delle partite)

