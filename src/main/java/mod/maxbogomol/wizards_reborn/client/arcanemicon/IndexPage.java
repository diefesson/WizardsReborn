package mod.maxbogomol.wizards_reborn.client.arcanemicon;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class IndexPage extends Page {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon_index_page.png");
    IndexEntry[] entries;

    public IndexPage(IndexEntry... pages) {
        super(BACKGROUND);
        this.entries = pages;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean click(ArcanemiconGui gui, int x, int y, int mouseX, int mouseY) {
        for (int i = 0; i < entries.length; i ++) if (entries[i].isUnlocked()) {
            if (mouseX >= x + 2 && mouseX <= x + 124 && mouseY >= y + 8 + i * 20 && mouseY <= y + 26 + i * 20) {
                gui.changeChapter(entries[i].chapter);
                Minecraft.getInstance().player.playNotifySound(SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0f, 1.0f);
                return true;
            }
        }
        return false;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(ArcanemiconGui book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        for (int i = 0; i < entries.length; i ++) {
            gui.blit(BACKGROUND, x, y + 7 + (i * 20), 128, 20, 128, 18);
        }

        for (int i = 0; i < entries.length; i ++) {
            if (entries[i].isUnlocked()) {
                gui.renderItem(entries[i].icon,x + 3, y + 8 + i * 20);
                gui.renderItemDecorations(Minecraft.getInstance().font, entries[i].icon,x + 3, y + 8 + i * 20, null);
                drawText(book, gui, I18n.get(entries[i].chapter.titleKey), x + 24, y + 20 + i * 20 - Minecraft.getInstance().font.lineHeight);
            } else {
                gui.blit(new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon_unknown.png"), x + 3, y + 8 + i * 20, 0, 0, 16, 16, 16, 16);
                drawText(book, gui, I18n.get("wizards_reborn.arcanemicon.unknown"), x + 24, y + 20 + i * 20 - Minecraft.getInstance().font.lineHeight);
            }
        }
    }
}