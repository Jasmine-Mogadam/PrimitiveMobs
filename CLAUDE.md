# PrimitiveMobs Agent Notes

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
