package dev.twme.worldeditdisplay;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

class PluginCompatibilityTest {
    @Test
    void descriptorTargetsMinecraft119() throws IOException {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("plugin.yml")) {
            assertNotNull(input);
            String descriptor = new String(input.readAllBytes(), StandardCharsets.UTF_8);
            assertTrue(descriptor.contains("api-version: '1.19'"));
        }
    }

    @Test
    void productClassesTargetJava17() throws IOException {
        String resource = WorldEditDisplay.class.getName().replace('.', '/') + ".class";
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(resource)) {
            assertNotNull(input);
            DataInputStream classFile = new DataInputStream(input);
            assertEquals(0xCAFEBABE, classFile.readInt());
            classFile.readUnsignedShort();
            assertEquals(61, classFile.readUnsignedShort());
        }
    }
}
