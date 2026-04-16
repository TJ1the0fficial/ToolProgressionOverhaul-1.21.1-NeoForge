package net.almmolt.toolprogressionoverhaul.datagen;

import hu.ammolt.pixelator.api.PixelatorGenerator;
import hu.ammolt.pixelator.dataGen.PixelatorDataProvider;
import hu.ammolt.pixelator.pixelator;
import net.almmolt.toolprogressionoverhaul.ToolProgressionOverhaul;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.PackOutput;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public class PixelatorProvider extends PixelatorDataProvider {
    private final PackOutput output;

    public PixelatorProvider(PackOutput output) {
        super(output);
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        // 1. Get the Project Root by going up from the 'run' folder
        // '..' tells Java to step out of 'run' and into the main project folder
        Path projectRoot = Path.of("").toAbsolutePath().getParent();

        // 2. Now we point to the REAL source and the REAL generated output
        Path sourceRoot = projectRoot.resolve("src").resolve("main").resolve("resources");
        Path outputRoot = output.getOutputFolder(); // This is already handled by NeoForge

        try {
            // 3. Set the paths and run
            PixelatorGenerator.setupPaths(pixelator.MODID, sourceRoot, outputRoot);
            PixelatorGenerator.generator(cache);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return CompletableFuture.completedFuture(null);
    }

    @Override
    public String getName() {
        // This makes the name unique!
        // e.g. "Pixelator: toolprogressionoverhaul"
        return "Pixelator: " + PixelatorGenerator.getModId();
    }
}
