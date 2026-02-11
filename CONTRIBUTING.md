# Contributing to Primitive Mobs

## Setting up the Development Environment

This project relies on several external dependencies and a specific Forge workspace setup. Please follow these steps to configure your environment correctly.

### 1. External Dependencies (JARs)

This project does not include library dependencies in the git repository. You must download the following mods (versions compatible with Minecraft 1.12.2) and place them in a `libs/` folder in the root directory:

1.  **Create the libs folder:**

    ```bash
    mkdir libs
    ```

2.  **Add the JARs:**
    Download and place these files into `libs/`:
    - **MultiMob** (Required): `multimob-1.0.5.jar` (or compatible version)
    - **Dynamic Trees** (Optional but recommended): `DynamicTrees-1.12.2-0.9.25.jar`
    - **Just Enough Resources (JER)** (Optional but recommended): `JustEnoughResources-1.12.2-0.9.2.60.jar`

    _Note: Ensure you are using the versions for Minecraft 1.12.2. Using versions for other Minecraft versions (like 1.9.4) will cause build errors._

### 2. Workspace Setup (Mappings)

Before building the project, you must set up the decompiled workspace to apply the correct MCP mappings. If you skip this step, you may see errors like `cannot find symbol variable collidedHorizontally` or method signature mismatches.

Run the following command:

```bash
./gradlew clean setupDecompWorkspace
```

_(On macOS/Linux, if you get a "permission denied" error, run `chmod +x gradlew` first)._

### 3. Building the Mod

Once dependencies are in place and the workspace is set up, you can build the mod using:

```bash
./gradlew build
```

The compiled JAR will be located in `build/libs/`.

### Troubleshooting

- **Missing Dependencies**: If you see errors like `package net.daveyx0.multimob... does not exist`, verify that `multimob-1.0.5.jar` is present in the `libs/` folder.
- **Mapping Issues**: If you see errors like `incompatible types: EntityPlayer cannot be converted to BlockPos` or missing fields/methods on standard classes (`Entity`, `World`, etc.), your workspace mappings are incorrect. Run `./gradlew clean setupDecompWorkspace` to fix this.
