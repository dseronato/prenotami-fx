package br.com.dlqs.prenotamifx.thread;

import com.sun.javafx.scene.control.IntegerField;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.concurrent.*;

public class TimerThread implements Runnable{

    private final TextField usernameInput;
    private final PasswordField passwordInput;
    private final String chromePath;
    private final String musicPath;
    private final ChoiceBox choiceBox;

    public TimerThread(TextField usernameInput, PasswordField passwordInput, String chromePath, String musicPath, IntegerField instances, ChoiceBox choiceBox) {
        this.usernameInput = usernameInput;
        this.passwordInput = passwordInput;
        this.chromePath = chromePath;
        this.musicPath = musicPath;
        this.choiceBox = choiceBox;
    }

    @Override
    public void run() {
        if(usernameInput.getText() != null && passwordInput.getText() != null)
            for (int i=1; i<=100; i++) {
                System.out.printf("Log-in %d%n",i );
                executeThreadForDuration(usernameInput.getText(),passwordInput.getText(),chromePath,musicPath,15L);
            }
    }

    private void executeThreadForDuration(String username, String password,String chromePath,String musicPath, long timeout)  {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        PrenotaThread prenota = new PrenotaThread(username, password, chromePath, musicPath, (String) choiceBox.getValue());
        Future future = executor.submit(prenota);
        try {
            future.get(timeout, TimeUnit.MINUTES);
        } catch (TimeoutException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
            finalizeThread(prenota);
            future.cancel(true);
        }  finally {
            finalizeThread(prenota);
            executor.shutdownNow();
            System.out.println("Thread finished");
        }
    }

    private void finalizeThread(PrenotaThread prenota){
        if(!prenota.isReservationFound())
            prenota.finish();
    }
}
