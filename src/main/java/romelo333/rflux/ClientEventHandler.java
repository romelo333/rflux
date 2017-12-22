package romelo333.rflux;

import net.minecraftforge.common.MinecraftForge;

public class ClientEventHandler {

    public static void subscribeMe() {
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
    }


    /*

    private boolean checkedDebugSupport = false;

    @SubscribeEvent
    public void onRenderTickForDebugOutput(TickEvent.RenderTickEvent e) {
        if (e.phase == TickEvent.Phase.START && (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment")) {
            if (!checkedDebugSupport) {
                if (!GLContext.getCapabilities().GL_KHR_debug) {
                    System.out.println("Your driver doesn't support KHR_debug_output...");
                    return;
                } else {
                    System.out.println("KHR_debug_output is supported. Enabling OpenGL debug output!");
                }
                GL11.glEnable(KHRDebug.GL_DEBUG_OUTPUT);
                GL11.glEnable(KHRDebug.GL_DEBUG_OUTPUT_SYNCHRONOUS);
                KHRDebug.glDebugMessageCallback(new KHRDebugCallback(new KHRDebugCallback.Handler() {

                    @Override
                    public void handleMessage(int source, int type, int id, int severity, String message) {
                        String identityStr;
                        switch (type) {
                            case GL43.GL_DEBUG_TYPE_DEPRECATED_BEHAVIOR:
                                identityStr = "Deprecated Behavior";
                                break;
                            case GL43.GL_DEBUG_TYPE_ERROR:
                                identityStr = "Error";
                                break;
                            case GL43.GL_DEBUG_TYPE_MARKER:
                                identityStr = "Marker";
                                break;
                            case GL43.GL_DEBUG_TYPE_OTHER:
                                identityStr = "Other";
                                break;
                            case GL43.GL_DEBUG_TYPE_PERFORMANCE:
                                identityStr = "Performance";
                                break;
                            case GL43.GL_DEBUG_TYPE_POP_GROUP:
                                identityStr = "Pop Group";
                                break;
                            case GL43.GL_DEBUG_TYPE_PORTABILITY:
                                identityStr = "Portability";
                                break;
                            case GL43.GL_DEBUG_TYPE_PUSH_GROUP:
                                identityStr = "Push Group";
                                break;
                            case GL43.GL_DEBUG_TYPE_UNDEFINED_BEHAVIOR:
                                identityStr = "Undefined Behavior";
                                break;
                            default:
                                identityStr = "Unknown";
                                break;
                        }
                        switch (source) {
                            case GL43.GL_DEBUG_SOURCE_API:
                                identityStr += " | API";
                                break;
                            case GL43.GL_DEBUG_SOURCE_WINDOW_SYSTEM:
                                identityStr += " | Window System";
                                break;
                            case GL43.GL_DEBUG_SOURCE_SHADER_COMPILER:
                                identityStr += " | Shader Compiler";
                                break;
                            case GL43.GL_DEBUG_SOURCE_THIRD_PARTY:
                                identityStr += " | Third Party";
                                break;
                            case GL43.GL_DEBUG_SOURCE_APPLICATION:
                                identityStr += " | Application";
                                break;
                            case GL43.GL_DEBUG_SOURCE_OTHER:
                                identityStr += " | Other";
                                break;
                            default:
                                identityStr += " | Unknown";
                                break;
                        }
                        String idHex = "0x"+Integer.toHexString(id).toUpperCase(Locale.ROOT);
                        if (severity == GL43.GL_DEBUG_SEVERITY_NOTIFICATION) {
                            LogManager.getLogger("OpenGL").info("["+identityStr+"] "+idHex+" Notification: "+message);
                        } else if (severity == GL43.GL_DEBUG_SEVERITY_LOW) {
                            LogManager.getLogger("OpenGL").info("["+identityStr+"] "+idHex+" "+message);
                        } else if (severity == GL43.GL_DEBUG_SEVERITY_MEDIUM) {
                            LogManager.getLogger("OpenGL").warn("["+identityStr+"] "+idHex+" "+message);
                        } else if (severity == GL43.GL_DEBUG_SEVERITY_HIGH) {
                            LogManager.getLogger("OpenGL").error("["+identityStr+"] "+idHex+" "+message, new OpenGLException().fillInStackTrace());
                        }
                    }
                }));
                checkedDebugSupport = true;
            }
        }
    }
    */
}

