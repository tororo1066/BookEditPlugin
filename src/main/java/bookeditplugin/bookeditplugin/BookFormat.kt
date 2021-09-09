package bookeditplugin.bookeditplugin

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.event.HoverEvent
import org.bukkit.entity.Player
import org.bukkit.inventory.meta.BookMeta

object BookFormat {

    fun format(p : Player,book : BookMeta): BookMeta {
        val componentPages = ArrayList<Component>()
        val bookPages = book.pages

        for (bookpage in bookPages){

            val splits = bookpage.replace("&","§").split("\\")
            if (splits.isEmpty()){
                componentPages.add(text(bookpage.replace("&","§")))

            }else {
                val componentSplits = text()
                for (split in splits) {

                    val args = split.split(":")
                    if (args.isEmpty()) continue
                    if (split.contains("\n")) {
                        componentSplits.append(text(split))
                        continue
                    }
                    when (args[0]) {
                        "RunCMD", "0", "RC" -> {
                            if (args.size != 3) {
                                componentSplits.append(text(split))
                                continue
                            }
                            val text = args[1]
                            val cmd = args[2]
                            if (text.isBlank() || cmd.isBlank() || !p.hasPermission("bookedit.runcmd") && !p.hasPermission(
                                    "bookedit.allperm"
                                )
                            ) {
                                componentSplits.append(text(split))
                                continue
                            }

                            componentSplits.append(text(text).clickEvent(ClickEvent.runCommand(cmd)))
                            continue
                        }

                        "HoverText", "1", "HT" -> {
                            if (args.size != 3) {
                                componentSplits.append(text(split))
                                continue
                            }
                            val text = args[1]
                            val hover = args[2]
                            if (text.isBlank() || hover.isBlank() || !p.hasPermission("bookedit.hovertext") && !p.hasPermission(
                                    "bookedit.allperm"
                                )
                            ) {
                                componentSplits.append(text(split))
                                continue
                            }

                            componentSplits.append(text(text).hoverEvent(HoverEvent.showText(text(hover))))
                            continue
                        }

                        "ChangePage", "2", "CP" -> {
                            if (args.size != 3) {
                                componentSplits.append(text(split))
                                continue
                            }
                            val text = args[1]
                            val page = args[2].toIntOrNull()
                            if (text.isBlank() || page == null || !p.hasPermission("bookedit.changepage") && !p.hasPermission(
                                    "bookedit.allperm"
                                )
                            ) {
                                componentSplits.append(text(split))
                                continue
                            }

                            componentSplits.append(text(text).clickEvent(ClickEvent.changePage(page)))
                            continue
                        }

                        "Copy", "3", "C" -> {
                            if (args.size != 3) {
                                componentSplits.append(text(split))
                                continue
                            }
                            val text = args[1]
                            val copymessage = args[2]
                            if (text.isBlank() || copymessage.isBlank() || !p.hasPermission("bookedit.copy") && !p.hasPermission(
                                    "bookedit.allperm"
                                )
                            ) {
                                componentSplits.append(text(split))
                                continue
                            }

                            componentSplits.append(text(text).clickEvent(ClickEvent.copyToClipboard(copymessage)))
                            continue
                        }

                        "URL", "4", "U" -> {
                            if (args.size != 3) {
                                componentSplits.append(text(split))
                                continue
                            }
                            val text = args[1]
                            val url = args[2]
                            if (text.isBlank() || url.isBlank() || !p.hasPermission("bookedit.url") && !p.hasPermission(
                                    "bookedit.allperm"
                                )
                            ) {
                                componentSplits.append(text(split))
                                continue
                            }

                            componentSplits.append(text(text).clickEvent(ClickEvent.openUrl(url)))
                            continue
                        }

                        "HoverCMD", "5", "HCMD" -> {
                            if (args.size != 4) {
                                componentSplits.append(text(split))
                                continue
                            }
                            val text = args[1]
                            val cmd = args[2]
                            val hover = args[3]
                            if (text.isBlank() || cmd.isBlank() || hover.isBlank() || !(p.hasPermission("bookedit.runcmd") && p.hasPermission(
                                    "bookedit.hovertext"
                                )) && !p.hasPermission("bookedit.allperm")
                            ) {
                                componentSplits.append(text(split))
                                continue
                            }

                            componentSplits.append(
                                text(text).clickEvent(ClickEvent.runCommand(cmd)).hoverEvent(
                                    HoverEvent.showText(
                                        text(hover)
                                    )
                                )
                            )
                            continue
                        }

                        "HoverPage", "6", "HCP" -> {
                            if (args.size != 4) {
                                componentSplits.append(text(split))
                                continue
                            }
                            val text = args[1]
                            val page = args[2].toIntOrNull()
                            val hover = args[3]
                            if (text.isBlank() || page == null || hover.isBlank() || !(p.hasPermission("bookedit.changepage") && p.hasPermission(
                                    "bookedit.hovertext"
                                )) && !p.hasPermission("bookedit.allperm")
                            ) {
                                componentSplits.append(text(split))
                                continue
                            }

                            componentSplits.append(
                                text(text).clickEvent(ClickEvent.changePage(page)).hoverEvent(
                                    HoverEvent.showText(
                                        text(hover)
                                    )
                                )
                            )
                            continue
                        }

                        "HoverCopy", "7", "HC" -> {
                            if (args.size != 4) {
                                componentSplits.append(text(split))
                                continue
                            }
                            val text = args[1]
                            val copy = args[2]
                            val hover = args[3]
                            if (text.isBlank() || copy.isBlank() || hover.isBlank() || !(p.hasPermission("bookedit.copy") && p.hasPermission(
                                    "bookedit.hovertext"
                                )) && !p.hasPermission("bookedit.allperm")
                            ) {
                                componentSplits.append(text(split))
                                continue
                            }

                            componentSplits.append(
                                text(text).clickEvent(ClickEvent.copyToClipboard(copy)).hoverEvent(
                                    HoverEvent.showText(
                                        text(hover)
                                    )
                                )
                            )
                            continue
                        }

                        "HoverURL", "8", "HU" -> {
                            if (args.size != 4) {
                                componentSplits.append(text(split))
                                continue
                            }
                            val text = args[1]
                            val url = args[2]
                            val hover = args[3]
                            if (text.isBlank() || url.isBlank() || hover.isBlank() || !(p.hasPermission("bookedit.url") && p.hasPermission(
                                    "bookedit.hovertext"
                                )) && !p.hasPermission("bookedit.allperm")
                            ) {
                                componentSplits.append(text(split))
                                continue
                            }

                            componentSplits.append(
                                text(text).clickEvent(ClickEvent.openUrl(url)).hoverEvent(
                                    HoverEvent.showText(
                                        text(hover)
                                    )
                                )
                            )
                            continue
                        }


                    }



                    componentSplits.append(text(split))
                }


                componentPages.add(componentSplits.build())
            }
        }

        book.pages(componentPages)
        p.sendMessage("§a記入が完了しました")
        return book
    }
}