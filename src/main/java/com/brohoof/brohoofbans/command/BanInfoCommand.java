package com.brohoof.brohoofbans.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.brohoof.brohoofbans.Ban;
import com.brohoof.brohoofbans.BrohoofBansPlugin;
import com.brohoof.brohoofbans.Data;
import com.brohoof.brohoofbans.ExpireConverter;
import com.brohoof.brohoofbans.Settings;
import com.brohoof.brohoofbans.command.handlers.AbstractCommandHandler;

public class BanInfoCommand extends AbstractCommand {

    private ExpireConverter converter;

    public BanInfoCommand(BrohoofBansPlugin plugin, Data data, ExpireConverter converter, Settings settings) {
        super(plugin, data, settings);
        this.converter = converter;
    }

    public boolean execute(CommandSender sender, Ban ban, boolean wantsFullInfo) {
        if (wantsFullInfo) {
            if (sender.hasPermission("brohoofbans.baninfo.admin")) {
                sendFullData(sender, ban);
                return true;
            }
            sender.sendMessage(AbstractCommandHandler.NO_PERMISSION);
            return true;
        }
        if (sender.hasPermission("brohoofbans.baninfo")) {
            sendPartialData(sender, ban);
            return true;
        }
        sender.sendMessage(AbstractCommandHandler.NO_PERMISSION);
        return true;
    }

    public boolean execute(CommandSender sender, String playerName) {
        sender.sendMessage(ChatColor.YELLOW + playerName + " is " + ChatColor.RED + "not" + ChatColor.YELLOW + " banned.");
        return true;
    }

    private void sendPartialData(CommandSender sender, Ban ban) {
        if (ban.isSuspension())
            sendPartialSuspensionData(sender, ban);
        else
            sendPartialBanData(sender, ban);
    }

    private void sendPartialBanData(CommandSender sender, Ban ban) {
        sender.sendMessage(ChatColor.YELLOW + ban.getVictimName() + " " + ChatColor.RED + "is " + ChatColor.YELLOW + "banned.");
        sender.sendMessage(ChatColor.YELLOW + "Victim UUID is " + ban.getVictim().toString() + ". Their name is " + ban.getVictimName());
        if (ban.getExpires().equalsIgnoreCase("NEVER"))
            sender.sendMessage(ChatColor.YELLOW + "The Victim is banned for " + ChatColor.WHITE + ban.getReason() + ChatColor.YELLOW + ". This ban does " + ChatColor.RED + "not " + ChatColor.YELLOW + "expire.");
        else
            sender.sendMessage(ChatColor.YELLOW + "The Victim is banned for " + ChatColor.WHITE + ban.getReason() + ChatColor.YELLOW + ". This ban expires at " + ChatColor.WHITE + converter.getFriendlyTime(Long.parseLong(ban.getExpires())));
    }

    private void sendPartialSuspensionData(CommandSender sender, Ban ban) {
        sender.sendMessage(ChatColor.YELLOW + ban.getVictimName() + " " + ChatColor.RED + "is " + ChatColor.YELLOW + "suspended.");
        sender.sendMessage(ChatColor.YELLOW + "Victim UUID is " + ban.getVictim().toString() + ". Their name is " + ban.getVictimName());
        sender.sendMessage(ChatColor.YELLOW + "The Victim is suspended for " + ChatColor.WHITE + ban.getReason());
    }

    private void sendFullData(CommandSender sender, Ban ban) {
        if (ban.isSuspension())
            sendFullSuspensionData(sender, ban);
        else
            sendFullBanData(sender, ban);
    }

    private void sendFullBanData(CommandSender sender, Ban ban) {
        sender.sendMessage(ChatColor.YELLOW + ban.getVictimName() + " " + ChatColor.RED + "is " + ChatColor.YELLOW + "banned.");
        sender.sendMessage(ChatColor.YELLOW + "Victim UUID is " + ban.getVictim().toString() + ". Their name is " + ban.getVictimName());
        sender.sendMessage(ChatColor.YELLOW + "Issuer UUID is " + ban.getExecutor().toString() + ". Their name is " + ban.getExecutorName());
        sender.sendMessage(ChatColor.YELLOW + "The Issuer's IP is " + ban.getExecutorIP() + ". The Victim's IP is " + ban.getVictimIP());
        if (ban.getExpires().equals("NEVER"))
            sender.sendMessage(ChatColor.YELLOW + "The Victim is banned for " + ChatColor.WHITE + ban.getReason() + ChatColor.YELLOW + ". This ban does " + ChatColor.RED + "not " + ChatColor.YELLOW + "expire.");
        else
            sender.sendMessage(ChatColor.YELLOW + "The Victim is banned for " + ChatColor.WHITE + ban.getReason() + ChatColor.YELLOW + ". This ban expires at " + ChatColor.WHITE + converter.getFriendlyTime(Long.parseLong(ban.getExpires())));
    }

    private void sendFullSuspensionData(CommandSender sender, Ban ban) {
        sender.sendMessage(ChatColor.YELLOW + ban.getVictimName() + ChatColor.RED + "is " + ChatColor.YELLOW + "suspended.");
        sender.sendMessage(ChatColor.YELLOW + "Victim UUID is " + ban.getVictim().toString() + ". Their name is " + ban.getVictimName());
        sender.sendMessage(ChatColor.YELLOW + "Issuer UUID is " + ban.getExecutor().toString() + ". Their name is " + ban.getExecutorName());
        sender.sendMessage(ChatColor.YELLOW + "The Victim is suspended for " + ChatColor.WHITE + ban.getReason());
        sender.sendMessage(ChatColor.YELLOW + "The Issuer's IP is " + ban.getExecutorIP() + ". The Victim's IP is " + ban.getVictimIP());
    }
}
