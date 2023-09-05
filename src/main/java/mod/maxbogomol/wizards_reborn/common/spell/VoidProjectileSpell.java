package mod.maxbogomol.wizards_reborn.common.spell;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.spell.Spell;

import java.awt.*;

public class VoidProjectileSpell extends Spell {
    public VoidProjectileSpell(String id) {
        super(id);
        addCrystalType(WizardsReborn.VOID_CRYSTAL_TYPE);
    }

    public Color getColor() {
        return new Color(175, 140, 194);
    }
}