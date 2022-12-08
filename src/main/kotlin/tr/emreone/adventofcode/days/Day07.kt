package tr.emreone.adventofcode.days

import tr.emreone.utils.Logger.logger
import tr.emreone.utils.extensions.times

object Day07 {

    enum class NodeType {
        UNKNOWN,
        DIRECTORY,
        FILE
    }

    class FileSystem() {
        val root = Node(null, "", NodeType.DIRECTORY)
        var currentNode: Node = root

        fun pwd(): String {
            return pathOf(currentNode)
        }

        companion object {
            fun calcSizeOf(node: Node): Long {
                if (node.type == NodeType.FILE) {
                    return node.weight ?: 0
                }
                return node.children.sumOf { calcSizeOf(it) }
            }

            fun pathOf(node: Node): String {
                return if (node.parent == null) {
                    "/"
                } else {
                    pathOf(node.parent) + node.fileName + "/"
                }
            }
        }
    }

    data class Node(
        val parent: Node?,
        var fileName: String,
        var type: NodeType = NodeType.UNKNOWN,
        var weight: Long? = null,
    ) {
        val children = mutableListOf<Node>()
    }

    private fun parseFileSystem(input: List<String>): FileSystem {
        val fileSystem = FileSystem()

        var lineIndex = 0
        val commandLinePatter = "\\$\\s(cd|ls)\\s?(/|\\w+|..)?".toRegex()
        while (lineIndex < input.size) {
            var line = input[lineIndex].trim()
            if (commandLinePatter.matches(line)) {
                val tokens = line.split(" ")
                when (tokens[1]) {
                    "cd" -> {
                        if (tokens[2] == "/") {
                            fileSystem.currentNode = fileSystem.root
                            logger.debug { "navigate to root -> " + fileSystem.pwd() }
                        } else if (tokens[2] == "..") {
                            fileSystem.currentNode = fileSystem.currentNode.parent!!
                            logger.debug { "navigate level up ->" + fileSystem.pwd() }
                        } else {
                            val enterNode = tokens[2]
                            fileSystem.currentNode = fileSystem.currentNode.children.first { it.fileName == enterNode }
                            logger.debug { "navigate to $enterNode -> " + fileSystem.pwd() }
                        }
                        lineIndex++
                    }

                    "ls" -> {
                        lineIndex++
                        logger.debug { "list files in " + fileSystem.pwd() }
                        do {
                            line = input[lineIndex]
                            if (line.startsWith("dir")) {
                                val (dirLabel, dirName) = line.split(" ")
                                fileSystem.currentNode.children.add(
                                    Node(
                                        fileSystem.currentNode,
                                        dirName,
                                        NodeType.DIRECTORY
                                    )
                                )
                                logger.debug("add directory to ${fileSystem.pwd()} -> $dirName")
                            } else {
                                val (fileSize, fileName) = line.split(" ")
                                fileSystem.currentNode.children.add(
                                    Node(
                                        fileSystem.currentNode,
                                        fileName,
                                        NodeType.FILE,
                                        fileSize.toLong()
                                    )
                                )
                                logger.debug("add file to ${fileSystem.pwd()} -> $fileName")
                            }
                        } while (lineIndex + 1 < input.size && !input[++lineIndex].startsWith("$"))
                    }
                }
            } else {
                lineIndex++
            }
        }

        return fileSystem
    }

    private fun printNodeWithChildren(node: Node, indent: Int = 0) {
        logger.debug { "${" " * indent} - ${if (node.parent == null) "/" else node.fileName}" }
        when (node.type) {
            NodeType.DIRECTORY -> {
                logger.debug { " (dir)\n" }
                node.children.forEach { child ->
                    printNodeWithChildren(child, indent + 2)
                }
            }

            NodeType.FILE -> {
                logger.debug { " (file, size=${node.weight})\n" }
            }

            NodeType.UNKNOWN -> {
                // do nothing
            }
        }
    }

    fun part1(input: List<String>): Long {
        val fileSystem = parseFileSystem(input)

        printNodeWithChildren(fileSystem.root, 0)

        var total = 0L
        fun sumAllDirectoriesWithLowerThanN(node: Node, maxWeight: Long) {
            if (node.type == NodeType.DIRECTORY) {
                val size = FileSystem.calcSizeOf(node)
                if (size < maxWeight) {
                    total += size
                }
                node.children.forEach { sumAllDirectoriesWithLowerThanN(it, maxWeight) }
            }
        }
        sumAllDirectoriesWithLowerThanN(fileSystem.root, 100_000L)
        return total
    }

    fun part2(input: List<String>): Long {
        val fileSystem = parseFileSystem(input)

        val totalSpace = 70_000_000L
        val minimumFreeSpace = 30_000_000L

        val currentSize = FileSystem.calcSizeOf(fileSystem.root)

        val matchingDirs = mutableListOf<Node>()

        fun collectMatchingDirectory(node: Node) {
            for (child in node.children.filter { it.type == NodeType.DIRECTORY }) {
                val size = FileSystem.calcSizeOf(child)
                if (currentSize - size < totalSpace - minimumFreeSpace) {
                    matchingDirs.add(child)
                    collectMatchingDirectory(child)
                }
            }
        }
        collectMatchingDirectory(fileSystem.root)
        return matchingDirs.minOf { FileSystem.calcSizeOf(it) }
    }
}
