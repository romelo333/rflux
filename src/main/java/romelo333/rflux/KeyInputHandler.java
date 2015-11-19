package romelo333.rflux;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import romelo333.rflux.network.PacketHandler;
import romelo333.rflux.network.PacketToggleMode;

public class KeyInputHandler {

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (KeyBindings.wandModifier.isPressed()) {
            PacketHandler.INSTANCE.sendToServer(new PacketToggleMode());
        }
    }
}
