<div align="center">

I like cats

</div>

---

Thank you for considering contributing to The Rot!

Below are some guidelines to help you get started.

# ⎯⎯  How Can I Contribute?⎯⎯

### Reporting Bugs
- Use the GitHub Issue Tracker.
- Provide a clear and descriptive title.
- Describe the steps to reproduce the bug.
- Attach logs or screenshots if possible.

### Suggesting Enhancements/Feature Requests
- Open an issue to discuss your idea.
- Be as descriptive as possible about the feature and why it's useful.

### Pull Requests
- 1. Fork the repository and create your branch from `dev`.
- 2. If you've added code that should be tested, add tests for it.
- 3. Ensure the project builds locally.
- 4. Follow the existing coding style.
- 5. Provide a clear description of the changes in your Pull Request.

# ⎯⎯  Code Style⎯⎯
- Follow standard Java naming conventions.
- Use 4 spaces for indentation.
- Add Javadoc for complex logic or public APIs.
- Keep methods small, should be able to explain in one sentance.

This is also a reminder for my memory problem.
## Keep methods small
If you can’t explain a method in one sentence, it’s too big.

**Bad:**
```
void handleEverything() { ... }
```
**Good:**
```
void handleJoin()
void setupPlayer()
void giveStartingItems()
```

## Avoid magic numbers

**Bad:**
```
val speed = 0.15
```
**Good:**
```
const val PLAYER_SPEED = 0.15
```

---

By contributing, you agree that your contributions will be licensed under its [MIT Code & ARR Assets License](LICENSE.md).
