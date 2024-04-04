#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct tn {
    char* name;
    struct tn* left;
    struct tn* right;
} treeNode;

treeNode* NewTreeNode(const char* name, treeNode* left, treeNode* right)
{
    treeNode* new_node;

    new_node = (treeNode*)malloc(sizeof(treeNode));

    new_node->name = strdup(name);
    new_node->left = left;
    new_node->right = right;

    return new_node;
}

int NameCheck(const char *name, treeNode* tree)
{
    int match;
    if (strcmp(name, tree->name) == 0) {
        match = 1;
    } else {
        printf("Failure: name=%s, tree->name=%s\n", name, tree->name);
        match = 0;
    }
    if (tree->left == NULL) {
        return match;
    } else {
        return match && NameCheck(name, tree->left) && NameCheck(name, tree->right);
    }
}

treeNode* BottomUpTree(const char *name, unsigned depth)
{
    if (depth > 0)
        return NewTreeNode
        (
            name,
            BottomUpTree(name, depth - 1),
            BottomUpTree(name, depth - 1)
        );
    else
        return NewTreeNode(name, NULL, NULL);
}

void DeleteTree(treeNode* tree)
{
    if (tree != NULL) {
        DeleteTree(tree->left);
        DeleteTree(tree->right);
        free(tree->name);
        free(tree);
    }
}

int main(int argc, char* argv[])
{
    if (argc < 3) {
        fprintf(stderr, "Usage: %s <depth> <name>\n", argv[0]);
        return EXIT_FAILURE;
    }

    const char *name;
    unsigned depth;
    treeNode *tree;

    depth = atol(argv[1]);
    name = argv[2];

    tree = BottomUpTree(name, depth);
    int result = NameCheck(name, tree);
    DeleteTree(tree);

    printf("Name check result = %d\n", result);

    return 0;
}
