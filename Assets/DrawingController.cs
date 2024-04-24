using UnityEngine;
using UnityEngine.InputSystem;

public class DrawingController : MonoBehaviour
{
    public LineRenderer lineRendererPrefab;
    private LineRenderer currentLineRenderer;

    void Update()
    {
        // Check if Mouse or Touchscreen is available before checking if they are pressed
        if ((Mouse.current != null && Mouse.current.leftButton.isPressed) ||
            (Touchscreen.current != null && Touchscreen.current.primaryTouch.press.isPressed))
        {
            Vector2 position = Mouse.current?.position.ReadValue() ?? Touchscreen.current.primaryTouch.position.ReadValue();
            if (currentLineRenderer == null)
            {
                StartLine(position);
            }
            else
            {
                UpdateLine(position);
            }
        }
        else
        {
            currentLineRenderer = null;
        }
    }

    void StartLine(Vector2 position)
    {
        if (lineRendererPrefab == null) return; // Prevent attempting to instantiate a null prefab

        currentLineRenderer = Instantiate(lineRendererPrefab, this.transform);
        var camera = Camera.main; // Store reference to prevent multiple accesses
        if (camera == null) return; // Check if the camera is available

        Vector3 worldPosition = camera.ScreenToWorldPoint(new Vector3(position.x, position.y, 10));
        currentLineRenderer.SetPosition(0, worldPosition);
        currentLineRenderer.SetPosition(1, worldPosition);
    }

    void UpdateLine(Vector2 position)
    {
        if (currentLineRenderer == null) return;

        var camera = Camera.main;
        if (camera == null) return;

        currentLineRenderer.positionCount++;
        Vector3 worldPosition = camera.ScreenToWorldPoint(new Vector3(position.x, position.y, 10));
        currentLineRenderer.SetPosition(currentLineRenderer.positionCount - 1, worldPosition);
    }
}
