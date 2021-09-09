package bookeditplugin.bookeditplugin

import io.papermc.paper.event.player.AsyncChatEvent
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.inventory.meta.BookMeta
import org.bukkit.plugin.java.JavaPlugin

class BookEdit : JavaPlugin(){

    override fun onEnable() {
        getCommand("bookedit")?.setExecutor(this)
    }



    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player)return true
        if (args.isEmpty()){
            val item = sender.inventory.itemInMainHand
            if (item.type == Material.AIR || item.itemMeta !is BookMeta){
                sender.sendMessage("§4手に署名済みの本を持ってください")
                sender.sendMessage("§aコマンドのヘルプを見るには/be helpを実行してください")
                return true
            }

            val meta = item.itemMeta as BookMeta
            if (!meta.hasAuthor()){
                sender.sendMessage("§4手に署名済みの本を持ってください")
                return true
            }

            if (meta.author != sender.name){
                sender.sendMessage("§4他人の本を改変することはできません")
                return true
            }

            val bookmeta = BookFormat.format(sender,meta)

            item.itemMeta = bookmeta
            return true

        }

        when(args[0]){
            "help"->{
                sender.sendMessage("§a=============BookEditPlugin====================")
                sender.sendMessage("§b/be 手に持っている記入済みの本を書き換えます")
                sender.sendMessage("§b/be help このメッセージを表示します")
                sender.sendMessage("§b/be howto 書き込みの方法を表示します")
                sender.sendMessage("§b/be list 書き込み方法のリストを表示します")
                sender.sendMessage("§a=============BookEditPlugin==Author:tororo_1066")
                return true
            }

            "howto"->{
                sender.sendMessage("§aBookEditPluginの使い方")
                sender.sendMessage("§b1.使用したい方法を選択して、\\<使用したいものの名前>:etc...\\ と本に記入します")
                sender.sendMessage("§b(記入の種類は/be list!)")
                sender.sendMessage("§b(カラーコードは&でできます)")
                sender.sendMessage("§d2.本を署名します")
                sender.sendMessage("§e3.その本を持って/beを実行します")
                sender.sendMessage("§6できあがり！")
                return true
            }

            "list"->{
                sender.sendMessage("§a記入種類リスト一覧")
                sender.sendMessage("§e\\RunCMD:<テキスト>:<コマンド>\\ §bid:0")
                sender.sendMessage("§e\\HoverText:<テキスト>:<ホバーテキスト>\\ §bid:1")
                sender.sendMessage("§e\\ChangePage:<テキスト>:<移動するページ>\\ §bid:2")
                sender.sendMessage("§e\\Copy:<テキスト>:<コピーするテキスト>\\ §bid:3")
                sender.sendMessage("§e\\URL:<テキスト>:<URL> §bid:4")
                sender.sendMessage("§dここから下はHoverTextとの複合です")
                sender.sendMessage("§e\\HoverCMD:<テキスト>:<コマンド>:<ホバーテキスト>\\ §bid:5")
                sender.sendMessage("§e\\HoverPage:<テキスト>:<移動するページ>:<ホバーテキスト>\\ §bid:6")
                sender.sendMessage("§e\\HoverCopy:<テキスト>:<コピーするテキスト>:<ホバーテキスト>\\ §bid:7")
                sender.sendMessage("§e\\HoverURL:<テキスト>:<URL>:<ホバーテキスト>\\ id:8")
                sender.sendMessage("§c\\<id>:<テキスト>:etc...\\でも可能です")
            }

        }

        return false

    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ): MutableList<String> {
        if (alias == "bookedit" || alias == "bedit" || alias == "be"){

            if (args.size == 1){
                return mutableListOf("help","list","howto")
            }
        }
        return mutableListOf()
    }

}