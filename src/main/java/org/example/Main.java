package org.example;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) {
        try {
            TelegramBotsApi botApi = new TelegramBotsApi(DefaultBotSession.class);
            botApi.registerBot(new BotLogic());
            System.out.println("Bot iniciado");
        } catch (Exception e) {
            System.err.println("Erro ao iniciar o bot: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
