# PrimitiveMobs Agent Notes

## Current State (read first)

You are in the `PrimitiveMobs-1.20.1` worktree (branch `mc/1.20.1`) — the **active** 1.20.1 Forge port. This is the only place to write code.

Done so far (commit "Modern Forge 1.20.1 bootstrap + Dodo vertical slice", builds green):
- Modern Forge bootstrap: `build.gradle` (ForgeGradle 6, Forge 1.20.1-47.4.10, Java 17, Gradle 8.8), `settings.gradle`, templated `META-INF/mods.toml` + `pack.mcmeta`. Jar archive name: `primitivemobswondersapped`.
- `DeferredRegister` registries: `registry/PrimitiveMobsItems.java`, `registry/PrimitiveMobsEntityTypes.java`.
- Items: `wonder_sap`, `dodo` (raw), `cooked_dodo`, `dodo_egg`, `mimic_orb`, `dodo_spawn_egg`.
- **Dodo** = the reference vertical slice. Copy its shape for every other mob: `entity/passive/Dodo.java` (extends vanilla `Chicken`), `client/renderer/entity/DodoRenderer.java`, `client/model/DodoModel.java`, `client/PrimitiveMobsClientEvents.java` (renderer + layer registration), texture under `assets/.../textures/entity/`, loot at `data/.../loot_tables/entities/dodo.json`, attributes in `registry/PrimitiveMobsEntityTypes#registerAttributes`, spawn via `data/.../forge/biome_modifier/` + `SpawnPlacements.register` in `PrimitiveMobsMod`.

~43 mobs remain unported. The full 1.12 implementation lives on the `mc/1.12` branch / `../PrimitiveMobs-1.12` worktree — **read-only reference**, do not edit it.

## MultiMob: absorb per-mob (do NOT port it as a separate mod)

The original 1.12 PrimitiveMobs depends on the **MultiMob** library mod (`net.daveyx0.multimob`, vendored as `libs/multimob-1.0.5.jar` on the 1.12 branch) in ~51 files — `IMultiMob`, tameable/variant capabilities, custom AI goals, spawn registry, particles, messages. MultiMob source is at `/Users/jazmo/Documents/GitHub/MultiMob` (1.12.2, read-only reference).

Strategy for this port:
- **Do not** recreate MultiMob as a subproject or compile-time dependency.
- As each mob is ported, pull in **only** the behavior it needs (AI goals, variant logic), rewritten under the `net.daveyx0.primitivemobs` package against native 1.20.1 APIs.
- **Drop** MultiMob's infrastructure (capabilities framework, entity/item registries, config GUI, item-model manager) — Forge 1.20.1 + vanilla provide equivalents (`DeferredRegister`, data-driven models, datapack spawns, etc.).
- Prefer extending the closest vanilla entity (as Dodo extends `Chicken`) over reproducing MultiMob base classes like `EntityMMBird`/`EntityMMJumper`.

## Port-the-next-mob recipe

1. Pick one mob. Read its 1.12 source under `../PrimitiveMobs-1.12/src/main/java/net/daveyx0/primitivemobs/entity/` (and any MultiMob bits it uses) as a **behavior spec**, not code to copy verbatim.
2. Write the entity under `entity/`, extending the nearest vanilla mob; reimplement AI/behavior with native 1.20.1 goals.
3. Register the `EntityType` in `PrimitiveMobsEntityTypes`, add attributes, add a spawn-egg item if it had one.
4. Add renderer + model + client registration (mirror the Dodo files); reuse the 1.12 texture/sound assets when compatible.
5. Add loot table, lang entries, spawn (biome modifier + `SpawnPlacements`).
6. Run `./gradlew build` until green, then commit that one slice. **Do not** add a `Co-Authored-By: Claude` trailer.

Reference paths:
- 1.12 source: `../PrimitiveMobs-1.12/src/main/`
- MultiMob source: `/Users/jazmo/Documents/GitHub/MultiMob/main/java/`

---

This repository should use one long-lived branch per supported Minecraft major version.

## Canonical Version Strategy

- Use `mc/1.12` for the legacy Forge 1.12.2 line.
- Use `mc/1.20.1` for the modern Forge 1.20.1 line.
- Keep gameplay intent aligned across branches when reasonable.
- Allow implementation details, APIs, resource formats, and build logic to diverge by branch.

This repo should not use a shared multi-version source tree with Gradle overrides unless there is a very small, clearly bounded need. For PrimitiveMobs, split branches are the default because they:

- spend fewer tokens during AI-assisted work
- reduce the risk of mixing incompatible Forge and Minecraft APIs
- keep Gradle and mappings easier to reason about
- improve code quality by letting each version use native patterns

## Preferred Local Workflow

Use `git worktree` so each Minecraft version can live in its own folder at the same time.

Example layout:

```bash
PrimitiveMobs-1.12    -> branch mc/1.12
PrimitiveMobs-1.20.1  -> branch mc/1.20.1
```

Example commands:

```bash
git branch mc/1.12
git branch mc/1.20.1
git worktree add ../PrimitiveMobs-1.12 mc/1.12
git worktree add ../PrimitiveMobs-1.20.1 mc/1.20.1
```

Work in one worktree at a time when using AI agents or editor automation.

## Source Of Truth For Parity

- Feature parity is tracked at the gameplay and content level, not by keeping source files identical.
- Branches may have different class layouts, registries, resource formats, data generation, or rendering code.
- Cherry-pick only small commits that are logic-only, docs-only, or otherwise version-agnostic.
- Reimplement API-heavy changes separately on each branch when Forge or Minecraft internals differ.

When porting content, treat the source branch as a spec reference:

- copy the intended mob behavior
- copy balance values and loot intent
- reuse textures, sounds, and raw assets when compatible
- rewrite code to match the target branch's native Forge patterns

## AI Coding Guardrails

- Agents should work against a single branch or worktree per task.
- Do not mix 1.12 MCP-era APIs with 1.20.1 Mojang-mapped APIs.
- Prefer branch-local fixes over compatibility abstractions.
- Avoid introducing shared-source Gradle complexity to save a few duplicated classes.
- Update branch docs or parity checklists when porting a mob, item, AI behavior, spawn rule, or renderer.

Before making versioned changes, confirm:

- the current branch and Minecraft target
- the Java version expected by that branch
- the Java runtime used to launch Gradle, not just the compiler target
- the mappings and Forge generation in use
- whether the change is a cherry-pick candidate or a branch-local reimplementation

## Branch Conventions

- `mc/1.12` and `mc/1.20.1` are the canonical long-lived branches.
- Short-lived feature branches should branch off the relevant version branch.
- Do not use `master` as the parity source of truth once version branches exist.

Recommended naming examples:

- `mc/1.12`
- `mc/1.20.1`
- `feature/1.12/<topic>`
- `feature/1.20.1/<topic>`

## Porting Workflow

1. Implement or fix a feature on the branch where the work starts.
2. Decide whether the resulting commit is safe to cherry-pick.
3. If not safe to cherry-pick, restate the change as behavior and port it manually.
4. Verify gameplay parity on the target branch.
5. Update any parity notes for mobs or systems touched by the port.

A mob or feature is considered "done" on both branches when:

- the intended gameplay behavior matches closely enough for players
- assets are present and wired correctly for that branch
- spawning, drops, sounds, AI, and rendering are validated for that branch
- branch-specific build steps still produce a jar

## Build And CI Notes

- GitHub Actions should build jars on pushes to version branches after merges land.
- Uploaded artifacts are for validation and download, not formal releases.
- The CI workflow should be branch-aware so the same workflow file can support `mc/1.12` now and `mc/1.20.1` later.
- Branch-specific Java setup is expected as the 1.20.1 line comes online.
- The 1.12 branch must be run with a Java 8 Gradle runtime. Using a newer JVM to launch Gradle can fail before project compilation, even if the source target is still Java 8.
- Future branches should document both their compiler target and their required Gradle runtime when those differ.

Current repo-specific constraint:

- The 1.12 build relies on local jars in `libs/`, including `multimob-1.0.5.jar`.
- CI can only build a branch successfully if that branch either vendors required dependencies or updates the build to fetch them from a repository.

If CI fails on a version branch, fix the branch-local build instead of adding cross-version build hacks.
