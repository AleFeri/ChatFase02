Diagramma dei casi d'uso: https://lucid.app/invitations/accept/54377dac-7991-4817-9761-60bc8036b69b

Utente:
    - l'username deve rispettare la regex "^[a-zA-Z0-9._-]{3,20}$" (es: "Usr1")
        - Non ci posso essere onimie: 2 o piu' utenti non possono avere lo stesso username (non gestite dal client)
    - per inviare un messaggio ad un utente deve scrivere "[nomeDestinatario]| [messaggio]" (es: "MarioRossi| Hello World!")
    - per inviare un messaggio multi-utente deve scrivere "[nomeDestinatario], [nomeDestinatario]| [messaggio]" (es: "MarioRossi, LuigiVerdi| Hello World!") (il multi messaggio non ha limiti di destinatari)
    - per inviare un messaggio al server deve scrivere "Server| [messaggio]"
        - i messaggi possibili sono:
            - "Server| HELP": il server risponde con la lista dei comandi
            - "Server| LIST": il server risponde con la lista degli utenti connessi
            - "Server| EXIT": il server ti disconnette (in locale cessano i Thread di supporto al Client (ClientRecive, ClientSend))

ClientRecive:
    - riceve i messaggi e li stampa sulla console del client
    - si occupa di gestire l'uscita del client

ClientSend:
    - si occupa di inoltrare i messaggi scritti dal client

Server:
    - si occupa di accettare gli utenti e di assegnargli un "canale di inoltro" (serverThread)

Server Thread:
    - riceve i messaggi dal suo utente designato e li inoltra al destinatario
    - gestisce i messaggi multi utente
    - gestisce i messaggi speciali fornendo la risposta ("Server| HELP", "Server| LIST", "Server| EXIT")

UserArray:
    - è il database degli utenti
    - consente il salvataggio/eliminazione/aggiornamento di un utente dal DB
    