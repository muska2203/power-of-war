package com.dreamteam.powerofwar.renderer;

import com.dreamteam.powerofwar.game.Board;
import com.dreamteam.powerofwar.game.event.AddObjectEvent;
import com.dreamteam.powerofwar.game.event.EventListener;
import com.dreamteam.powerofwar.game.event.StopGameEvent;
import com.dreamteam.powerofwar.game.object.GameObject;
import org.lwjgl.BufferUtils;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL13.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class GameRenderer implements Runnable {

    private static final double DEG2RAD = Math.PI / 180;
    private Board board;
    private EventListener eventListener;
    private long window;
    private long fps;

    public GameRenderer(Board board, EventListener eventListener) {
        this.board = board;
        this.eventListener = eventListener;
        fps = 60;
    }

    @Override
    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
        eventListener.registerEvent(new StopGameEvent());
    }

    private void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        // Create the window
        window = glfwCreateWindow((int) board.getWidth(), (int) board.getHeight(), "Hello World!", NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                glfwSetWindowShouldClose(window, true);
            }
        });
        glfwSetMouseButtonCallback(window, (window, button, action, mods) -> {
            if (button == GLFW_MOUSE_BUTTON_1 && action == GLFW_RELEASE) {
                DoubleBuffer xPosition = BufferUtils.createDoubleBuffer(1);
                DoubleBuffer yPosition = BufferUtils.createDoubleBuffer(1);
                glfwGetCursorPos(window, xPosition, yPosition);
                IntBuffer width = BufferUtils.createIntBuffer(1);
                IntBuffer height = BufferUtils.createIntBuffer(1);
                glfwGetWindowSize(window, width, height);
                eventListener.registerEvent(new AddObjectEvent(xPosition.get(0), height.get(0) - yPosition.get(0)));
            }
        });

        // Get the thread stack and push a new frame
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(0);

        // Make the window visible
        glfwShowWindow(window);
    }

    private void loop() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();
        long lastUpdate = System.nanoTime();


        IntBuffer w = BufferUtils.createIntBuffer(1);
        IntBuffer h = BufferUtils.createIntBuffer(1);
        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while (!glfwWindowShouldClose(window)) {
            if (System.nanoTime() - lastUpdate > 1000000 / fps) {
                // Set the clear color
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
                glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

                glfwGetWindowSize(window, w, h);
                int windowWidth = w.get(0);
                int windowHeight = h.get(0);
                double boardScale = Math.max(windowWidth / board.getWidth(), windowHeight / board.getHeight());
                windowWidth *= boardScale;
                windowHeight *= boardScale;

                glColor4f(0.0f, 1.0f, 0.0f, 0.0f);
                glMatrixMode(GL_MODELVIEW);
                for (GameObject gameObject : board.getGameObjects()) {
                    double size = convertWidth(gameObject.getSize(), windowWidth, boardScale) * 5;
                    double xPosition = convertWidth(gameObject.getX(), windowWidth, boardScale) * 2 - 1;
                    double yPosition = convertWidth(gameObject.getY(), windowHeight, boardScale) * 2 - 1;
                    drawCircle(xPosition, yPosition, size);
                }
                lastUpdate = System.nanoTime();
            }
            // draw quad
            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }

    private void drawCircle(double x, double y, double radius) {
        glBegin(GL_POLYGON);

        for (int i = 0; i < 360; i++) {
            double degInRad = i * DEG2RAD;
            glVertex2d(Math.sin(degInRad) * radius + x, Math.cos(degInRad) * radius + y);
        }

        glEnd();
    }

    private void drawQuad(double x, double y, double width, double height) {
        glBegin(GL_QUADS);

        glVertex2d(x - width, y - height);
        glVertex2d(x + width, y - height);
        glVertex2d(x + width, y + height);
        glVertex2d(x - width, y + height);

        glEnd();
    }

    private double convertWidth(double size, double windowSize, double boardScale) {
        return (size * boardScale) / windowSize;
    }
}
