# jesusQuiztus

A 2D multiplayer quiz-combat game built with Greenfoot and Java, developed as part of a 10th grade school project by **Moritz Deinzer & Tim Burkhardt**.

Players compete in real-time PvP rounds where answering questions correctly fuels combat mechanics — wrong answers have consequences, right answers give you the edge.

---

## Features

**Multiplayer**
- Host and join sessions over a local network
- Standalone dedicated server program available for self-hosting (see Releases)

**Gameplay**
- Quiz-based combat — questions determine the flow of each round
- PvP mechanics that reward knowledge and punish mistakes
- Power-ups and items to shift momentum mid-match
- Scoreboard system tracking performance across rounds

---

## Requirements

- [Greenfoot](https://www.greenfoot.org/) — required to run the project from source
- Java 11 or newer

---

## Getting Started

**Play directly**
Download the latest release from the [Releases page](https://github.com/Moritydeinyer/jesusQuiztus/releases) and run the executable.

**Open in Greenfoot**
1. Clone the repository
2. Open the project folder in Greenfoot
3. Press Run

**Self-host a dedicated server**
A beta release of the dedicated server program is available on the Releases page. Download, extract, and run the server binary — clients can then connect via IP.

---

## Project Structure

| Path | Contents |
|---|---|
| `images/` | Sprite and UI assets |
| `sounds/` | Sound effects and music |

---

## Licensing

- **Source code** — MIT License (see [`LICENSE.txt`](./LICENSE.txt))
- **Third-party assets** — Individual licenses listed in [`CREDITS.md`](./CREDITS.md)

---

## About

jesusQuiztus was developed as a 10th grade computer science project. It was built entirely in Java using the Greenfoot framework, which handles the 2D game loop, rendering, and input.
