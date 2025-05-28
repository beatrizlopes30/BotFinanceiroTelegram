package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.HashMap;
import java.util.Map;

class BotLogic extends TelegramLongPollingBot {
    private final Map<Long, Double> saldos = new HashMap<>();

    @Override
    public String getBotUsername() {
        return "ashdkhaskjdh_bot";
    }

    @Override
    public String getBotToken() {
        return "8006364191:AAHdVrFbqgZJrabcUsB3VuNxJtRUugizh3Q";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) return;
        long chatId = update.getMessage().getChatId();
        String msg = update.getMessage().getText();
        String response = "";

        try {
            if (msg.startsWith("/renda ")) {
                double renda = Double.parseDouble(msg.split(" ")[1]);
                saldos.put(chatId, renda);
                response = String.format("Renda registrada: R$%.2f", renda);
            } else if (msg.startsWith("/gasto ")) {
                double gasto = Double.parseDouble(msg.split(" ")[1]);
                double saldo = saldos.getOrDefault(chatId, 0.0);
                if (gasto > saldo) response = "Saldo insuficiente!(Tu tá lisa)";
                else {
                    saldos.put(chatId, saldo - gasto);
                    response = String.format("Gasto registrado. Saldo: R$%.2f", saldo - gasto);
                }
            } else if (msg.equals("/saldo")) {
                response = String.format("Seu saldo: R$%.2f", saldos.getOrDefault(chatId, 0.0));
            } else {
                response = "Comandos: /renda [valor], /gasto [valor], /saldo";
            }

            enviarMsg(chatId, response);
        } catch (Exception e) {
            enviarMsg(chatId, "Erro: formato inválido! Ex: /renda 1000");
        }
    }

    private void enviarMsg(long chatId, String texto) {
        try {
            execute(new SendMessage(String.valueOf(chatId), texto));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
